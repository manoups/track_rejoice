package one.breece.track_rejoice.service.impl

import jakarta.transaction.Transactional
import one.breece.track_rejoice.commands.PetAnnouncementCommand
import one.breece.track_rejoice.commands.APBResponse
import one.breece.track_rejoice.domain.Pet
import one.breece.track_rejoice.domain.PetSexEnum
import one.breece.track_rejoice.domain.SpeciesEnum
import one.breece.track_rejoice.web.dto.PetResponse
import one.breece.track_rejoice.repository.PetRepository
import one.breece.track_rejoice.service.GeocodingService
import one.breece.track_rejoice.service.PetService
import org.locationtech.jts.geom.GeometryFactory
import org.locationtech.jts.geom.Point
import org.locationtech.jts.geom.impl.PackedCoordinateSequenceFactory
import org.springframework.core.convert.converter.Converter
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*

@Service
class PetServiceImpl(
    private val repository: PetRepository,
    private val petToPetResponseMapper: Converter<Pet, PetResponse>,
    private val geocodingService: GeocodingService,
    private val petToAbpResponse: Converter<Pet, APBResponse>
) : PetService {
    @Transactional
    override fun createAPB(petAnnouncementCommand: PetAnnouncementCommand): APBResponse {
//        val lastSeenLocation = geocodingService.geocode(petAnnouncementCommand.address)!!
        val newPet = Pet(
            name = petAnnouncementCommand.name!!,
            lastSeenLocation = makePoint(petAnnouncementCommand.lon!!, petAnnouncementCommand.lat!!),
            species = SpeciesEnum.valueOf(petAnnouncementCommand.species!!.uppercase()),
            breed = petAnnouncementCommand.breed!!,
            sex = PetSexEnum.valueOf(petAnnouncementCommand.sex!!.uppercase()),
            color = petAnnouncementCommand.color,
        ).also {
            it.extraInfo = petAnnouncementCommand.additionalInformation
            it.phoneNumber =  petAnnouncementCommand.phoneNumber
            it.humanReadableAddress = petAnnouncementCommand.address
        }
//        newPet.addToTraceHistory(lastSeenLocation)

        val geofence = repository.save(newPet)
        return APBResponse(geofence.id!!, geofence.species.toString(), false, petAnnouncementCommand.name, petAnnouncementCommand.breed, petAnnouncementCommand.color, petAnnouncementCommand.phoneNumber, petAnnouncementCommand.address, petAnnouncementCommand.lastSeenDate!!, petAnnouncementCommand.additionalInformation)
    }

    private fun makePoint(lon: Double, lat: Double) : Point {
        val gf = GeometryFactory()
        val sf = PackedCoordinateSequenceFactory()
        return gf.createPoint(sf.create(doubleArrayOf(lon, lat), 2))
    }

    override fun deleteById(id: Long) {
        repository.deleteById(id)
    }

    override fun enableAnnouncement(announcementId: Long) {
        repository.findById(announcementId).ifPresent{
            it.enabled = true
            repository.save(it)
        }
    }

    @Transactional
    override fun findAllByLngLat(lon: Double, lat: Double, distanceInMeters: Double, pageRequest: Pageable): Page<PetResponse> {
        return repository.findAllByLngLat(lon, lat, distanceInMeters, pageRequest).map { petToPetResponseMapper.convert(it)!! }
    }

    override fun findById(petId: Long): Optional<PetResponse> {
        return repository.findById(petId).map { petToPetResponseMapper.convert(it) }
    }

    override fun findDistanceBetween(id1: Long, id2: Long) = repository.findDistanceBetween(id1, id2)

    override fun findAll(): List<PetResponse> {
        return repository.findAll().map { petToPetResponseMapper.convert(it)!! }
    }

    override fun readById(id: Long): APBResponse? {
        return repository.findById(id).map { petToAbpResponse.convert(it) }.orElse(null)
    }
}