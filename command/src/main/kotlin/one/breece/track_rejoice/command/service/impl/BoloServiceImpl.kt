package one.breece.track_rejoice.command.service.impl


import one.breece.track_rejoice.command.domain.Bicycle
import one.breece.track_rejoice.command.domain.Item
import one.breece.track_rejoice.command.domain.Pet
import one.breece.track_rejoice.command.repository.BeOnTheLookOutRepository
import one.breece.track_rejoice.command.service.BoloService
import one.breece.track_rejoice.command.state.machine.BoloEvents
import one.breece.track_rejoice.command.state.machine.BoloStateChangeInterceptor
import one.breece.track_rejoice.core.domain.BoloStates
import one.breece.track_rejoice.core.projections.BeOnTheLookOutProj
import org.springframework.core.convert.converter.Converter
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.messaging.support.MessageBuilder
import org.springframework.statemachine.StateMachine
import org.springframework.statemachine.StateMachineContext
import org.springframework.statemachine.config.StateMachineFactory
import org.springframework.statemachine.support.DefaultStateMachineContext
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.util.*

@Service
class BoloServiceImpl(
    private val repository: BeOnTheLookOutRepository,
    private val petToProjCommandRepository: Converter<Pet, BeOnTheLookOutProj>,
    private val itemToProjCommandRepository: Converter<Item, BeOnTheLookOutProj>,
    private val bicycleToProjCommandRepository: Converter<Bicycle, BeOnTheLookOutProj>,
    private val factory: StateMachineFactory<BoloStates, BoloEvents>,
    private val stateChangeInterceptor: BoloStateChangeInterceptor
) : BoloService {
    companion object {
        const val BOLO_ID_HEADER = "bolo_id"
    }

    override fun activateAnnouncement(announcementId: Long) {
        build(announcementId).ifPresent {
            sendEvent(announcementId, it, BoloEvents.PUBLISH)
        }
    }

    override fun findAll(pageable: Pageable): Page<BeOnTheLookOutProj> {
        val bolos = repository.findAllByStateIn(listOf(BoloStates.DRAFT, BoloStates.ACTIVE), pageable)
        val responsePayload = bolos.content.map {
            when (it) {
                is Pet -> petToProjCommandRepository.convert(it).also { ptr -> ptr?.detailsUrl = "/bolo/form/pet/created/${ptr.sku}" }
                is Item -> itemToProjCommandRepository.convert(it).also { ptr -> ptr?.detailsUrl = "/bolo/form/item/created/${ptr.sku}" }
                is Bicycle -> bicycleToProjCommandRepository.convert(it).also { ptr -> ptr?.detailsUrl = "/bolo/form/transport/created/${ptr.sku}" }
                else -> throw IllegalArgumentException("Unknown type $it")
            }
        }
        return PageImpl(responsePayload, pageable, bolos.totalElements)

    }

    override fun deleteBySku(sku: UUID) {
        val optional = repository.findBySku(sku)
        if(optional.isEmpty) {
            throw RuntimeException("Bolo not found")
        }
        buildBySku(sku).ifPresent {
            sendEvent(optional.get().id!!, it, BoloEvents.DEACTIVATE)
        }
    }

    override fun markFoundBySku(sku: UUID) {
        val optional = repository.findBySku(sku)
        if(optional.isEmpty) {
            throw RuntimeException("Bolo not found")
        }
        buildBySku(sku).ifPresent {
            sendEvent(optional.get().id!!, it, BoloEvents.MARK_FOUND)
        }
    }

    private fun buildBySku(sku: UUID): Optional<StateMachine<BoloStates, BoloEvents>> {
        return repository.findBySku(sku).map {
            val stateMachine = factory.getStateMachine(it.id.toString())
            val stateMachineContext = DefaultStateMachineContext<BoloStates, BoloEvents>(it.state, null, null, null)
            return@map this.restoreStateMachine(stateMachine, stateMachineContext);
        }
    }

    private fun build(boloId: Long): Optional<StateMachine<BoloStates, BoloEvents>> {
        return repository.findById(boloId).map {
            val stateMachine = factory.getStateMachine(it.id.toString())
            val stateMachineContext = DefaultStateMachineContext<BoloStates, BoloEvents>(it.state, null, null, null)
            return@map this.restoreStateMachine(stateMachine, stateMachineContext);
        }
    }

    protected fun restoreStateMachine(
        stateMachine: StateMachine<BoloStates, BoloEvents>,
        stateMachineContext: StateMachineContext<BoloStates, BoloEvents>?
    ): StateMachine<BoloStates, BoloEvents> {
        if (stateMachineContext == null) {
            return stateMachine
        } else {
            stateMachine.stopReactively().block()
            stateMachine.stateMachineAccessor.doWithAllRegions {
                it.addStateMachineInterceptor(stateChangeInterceptor)
                it.resetStateMachineReactively(
                    stateMachineContext
                ).block()
                stateMachine.startReactively().block()
            }
            return stateMachine
        }
    }

    private fun sendEvent(boloId: Long, sm: StateMachine<BoloStates, BoloEvents>, event: BoloEvents) {
        val msg = MessageBuilder.withPayload(event).setHeader(BOLO_ID_HEADER, boloId).build()
        sm.sendEvent(Mono.just(msg)).subscribe()
    }
}