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
import one.breece.track_rejoice.web.dto.BoloResponse
import org.springframework.core.convert.converter.Converter
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*

@Service
class BoloServiceImpl(
    private val repository: BeOnTheLookOutRepository,
    private val petRepository: PetRepository,
    private val itemRepository: ItemRepository,
    private val bicycleRepository: BicycleRepository,
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
        lon: Double,
        lat: Double,
        distanceInMeters: Double,
        pageable: Pageable
    ): Page<BeOnTheLookOutProj> {
        val page = repository.findIdsByLngLat(lon, lat, distanceInMeters, pageable)
        val pets = petRepository.findAllById(page.content)
        val items = itemRepository.findAllById(page.content)
        val bicycles = bicycleRepository.findAllById(page.content)
        val petProj = pets.map { petToProjRepository.convert(it) }
        val itemProj = items.map { itemToProjRepository.convert(it) }
        val transportationProj = bicycles.map { bicycleToProjRepository.convert(it) }
        return PageImpl(petProj + itemProj + transportationProj, pageable, page.totalElements)
    }

    override fun findAll(pageable: Pageable): Page<BeOnTheLookOut> {
        return repository.findAll(pageable)

    }

    override fun deleteBySku(sku: UUID) {
        repository.finBySku(sku).ifPresent {
            repository.delete(it)
        }
    }
}