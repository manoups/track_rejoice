package one.breece.track_rejoice.service.impl

import one.breece.track_rejoice.domain.BeOnTheLookOut
import one.breece.track_rejoice.domain.Item
import one.breece.track_rejoice.domain.Bicycle
import one.breece.track_rejoice.domain.Pet
import one.breece.track_rejoice.repository.BeOnTheLookOutRepository
import one.breece.track_rejoice.repository.ItemRepository
import one.breece.track_rejoice.repository.PetRepository
import one.breece.track_rejoice.repository.BicycleRepository
import one.breece.track_rejoice.repository.projections.BeOnTheLookOutProj
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
        val bolos = repository.findIdsByLngLat(point, distanceInMeters, pageable)
//        TODO: Delegate to factory pattern
        val responsePayload = bolos.content.map {
            when(it) {
                is Pet -> petToProjRepository.convert(it)
                is Item -> itemToProjRepository.convert(it)
                is Bicycle -> bicycleToProjRepository.convert(it)
                else -> throw IllegalArgumentException("Unknown type")
            }
        }
        return PageImpl(responsePayload, pageable, bolos.totalElements)
    }

    override fun findAll(pageable: Pageable): Page<BeOnTheLookOut> {
        return repository.findAll(pageable)

    }

    override fun deleteBySku(sku: UUID) {
        repository.findBySku(sku).ifPresent {
            repository.delete(it)
        }
    }
}