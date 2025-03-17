package one.breece.track_rejoice.command.state.machine

import one.breece.track_rejoice.command.repository.BeOnTheLookOutRepository
import one.breece.track_rejoice.command.service.impl.BoloServiceImpl
import one.breece.track_rejoice.core.domain.BoloStates
import org.springframework.messaging.Message
import org.springframework.statemachine.StateMachine
import org.springframework.statemachine.state.State
import org.springframework.statemachine.support.StateMachineInterceptorAdapter
import org.springframework.statemachine.transition.Transition
import org.springframework.stereotype.Component

@Component
class BoloStateChangeInterceptor(private val repository: BeOnTheLookOutRepository) :
    StateMachineInterceptorAdapter<BoloStates, BoloEvents>() {
    override fun preStateChange(
        state: State<BoloStates, BoloEvents>,
        message: Message<BoloEvents>?,
        transition: Transition<BoloStates, BoloEvents>?,
        stateMachine: StateMachine<BoloStates, BoloEvents>?,
        rootStateMachine: StateMachine<BoloStates, BoloEvents>?
    ) {
        message?.let {
            it.headers[BoloServiceImpl.BOLO_ID_HEADER]?.let { boloId ->
                repository.findById(boloId as Long).map { bolo ->
                    bolo.state = state.id
                    repository.save(bolo)
                }
            }
        }
    }
}