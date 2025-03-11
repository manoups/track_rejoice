package one.breece.track_rejoice.service.impl

import one.breece.track_rejoice.domain.query.Bicycle
import one.breece.track_rejoice.domain.query.Item
import one.breece.track_rejoice.domain.query.Pet
import one.breece.track_rejoice.repository.command.BeOnTheLookOutRepository
import one.breece.track_rejoice.repository.projections.BeOnTheLookOutProj
import one.breece.track_rejoice.repository.query.BeOnTheLookoutQueryRepository
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
    private val beOnTheLookOutQueryRepository: BeOnTheLookoutQueryRepository,
    private val petToProjRepository: Converter<Pet, BeOnTheLookOutProj>,
    private val itemToProjRepository: Converter<Item, BeOnTheLookOutProj>,
    private val bicycleToProjRepository: Converter<Bicycle, BeOnTheLookOutProj>
) : BoloService {
    override fun enableAnnouncement(announcementId: Long) {
        repository.findById(announcementId).ifPresent {
            it.enabled = true
            repository.save(it)
        }
    }

    override fun findAllByLngLat(
        lng: Double,
        lat: Double,
        distanceInMeters: Double,
        pageable: Pageable
    ): Page<BeOnTheLookOutProj> {
        val point = GeometryUtil.parseLocation(lng, lat)
        val bolos = beOnTheLookOutQueryRepository.findIdsByLngLat(point, distanceInMeters, pageable)
//        TODO: Delegate to factory pattern
        val responsePayload = bolos.content.map {
            when(it) {
                is Pet -> petToProjRepository.convert(it)
                is Item -> itemToProjRepository.convert(it)
                is Bicycle -> bicycleToProjRepository.convert(it)
                else -> throw IllegalArgumentException("Unknown type $it")
            }
        }
        return PageImpl(responsePayload, pageable, bolos.totalElements)
    }

    override fun findAll(pageable: Pageable): Page<BeOnTheLookOutProj> {
        val bolos = beOnTheLookOutQueryRepository.findAll(pageable)
        val responsePayload = bolos.content.map {
            when(it) {
                is Pet -> petToProjRepository.convert(it)
                is Item -> itemToProjRepository.convert(it)
                is Bicycle -> bicycleToProjRepository.convert(it)
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