package one.breece.track_rejoice.service.impl


import one.breece.track_rejoice.core.projections.BeOnTheLookOutProj
import one.breece.track_rejoice.repository.command.BeOnTheLookOutRepository
import one.breece.track_rejoice.service.BoloService
import org.springframework.core.convert.converter.Converter
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*

@Service
class BoloServiceImpl(
    private val repository: BeOnTheLookOutRepository,
    private val petToProjCommandRepository: Converter<one.breece.track_rejoice.domain.command.Pet, BeOnTheLookOutProj>,
    private val itemToProjCommandRepository: Converter<one.breece.track_rejoice.domain.command.Item, BeOnTheLookOutProj>,
    private val bicycleToProjCommandRepository: Converter<one.breece.track_rejoice.domain.command.Bicycle, BeOnTheLookOutProj>
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
                is one.breece.track_rejoice.domain.command.Pet -> petToProjCommandRepository.convert(it)
                is one.breece.track_rejoice.domain.command.Item -> itemToProjCommandRepository.convert(it)
                is one.breece.track_rejoice.domain.command.Bicycle -> bicycleToProjCommandRepository.convert(it)
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