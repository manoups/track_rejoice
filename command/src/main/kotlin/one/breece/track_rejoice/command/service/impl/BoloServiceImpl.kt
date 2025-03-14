package one.breece.track_rejoice.command.service.impl


import one.breece.track_rejoice.command.domain.Bicycle
import one.breece.track_rejoice.command.domain.Item
import one.breece.track_rejoice.command.domain.Pet
import one.breece.track_rejoice.command.repository.BeOnTheLookOutRepository
import one.breece.track_rejoice.command.service.BoloService
import one.breece.track_rejoice.core.projections.BeOnTheLookOutProj
import org.springframework.core.convert.converter.Converter
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*

@Service
class BoloServiceImpl(
    private val repository: BeOnTheLookOutRepository,
    private val petToProjCommandRepository: Converter<Pet, BeOnTheLookOutProj>,
    private val itemToProjCommandRepository: Converter<Item, BeOnTheLookOutProj>,
    private val bicycleToProjCommandRepository: Converter<Bicycle, BeOnTheLookOutProj>
) : BoloService {
    override fun enableAnnouncement(announcementId: Long) {
        repository.findById(announcementId).ifPresent {
            it.enabled = true
            repository.save(it)
        }
    }

    override fun findAll(pageable: Pageable): Page<BeOnTheLookOutProj> {
        val bolos = repository.findAll(pageable)
        val responsePayload = bolos.content.map {
            when(it) {
                is Pet -> petToProjCommandRepository.convert(it)
                is Item -> itemToProjCommandRepository.convert(it)
                is Bicycle -> bicycleToProjCommandRepository.convert(it)
                else -> throw IllegalArgumentException("Unknown type $it")
            }
        }
        return PageImpl(responsePayload, pageable, bolos.totalElements)

    }

    override fun deleteBySku(sku: UUID) {
        repository.findBySku(sku).ifPresent {
            repository.delete(it)
        }
    }
}