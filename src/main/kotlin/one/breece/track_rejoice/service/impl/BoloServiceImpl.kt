package one.breece.track_rejoice.service.impl

import one.breece.track_rejoice.domain.Item
import one.breece.track_rejoice.domain.MeansOfTransportation
import one.breece.track_rejoice.domain.Pet
import one.breece.track_rejoice.repository.BeOnTheLookOutRepository
import one.breece.track_rejoice.repository.ItemRepository
import one.breece.track_rejoice.repository.PetRepository
import one.breece.track_rejoice.repository.TransportationRepository
import one.breece.track_rejoice.repository.projections.BeOnTheLookOutProj
import one.breece.track_rejoice.service.BoloService
import org.springframework.core.convert.converter.Converter
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class BoloServiceImpl(
    private val repository: BeOnTheLookOutRepository,
    private val petRepository: PetRepository,
    private val itemRepository: ItemRepository,
    private val transportationRepository: TransportationRepository,
    private val petToProjRepository: Converter<Pet, BeOnTheLookOutProj>,
    private val itemToProjRepository: Converter<Item, BeOnTheLookOutProj>,
    private val transportationToProjRepository: Converter<MeansOfTransportation, BeOnTheLookOutProj>
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
        val pets = petRepository.findAllByIdAnonymous(page.content)
        val items = itemRepository.findAllByIdAnonymous(page.content)
        val transportations = transportationRepository.findAllByIdAnonymous(page.content)
        val petProj = pets.map { petToProjRepository.convert(it) }
        val itemProj = items.map { itemToProjRepository.convert(it) }
        val transportationProj = transportations.map { transportationToProjRepository.convert(it) }
        return PageImpl(petProj + itemProj + transportationProj, pageable, page.totalElements)
    }
}