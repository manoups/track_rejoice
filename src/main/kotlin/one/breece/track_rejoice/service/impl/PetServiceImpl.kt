package one.breece.track_rejoice.service.impl

import jakarta.transaction.Transactional
import one.breece.track_rejoice.commands.PetAnnouncementCommand
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
    private val petToAbpResponse: Converter<Pet, one.breece.track_rejoice.commands.PetResponseCommand>
) : PetService {
    @Transactional
    override fun createAPB(announcementCommand: PetAnnouncementCommand): one.breece.track_rejoice.commands.PetResponseCommand {
//        val lastSeenLocation = geocodingService.geocode(petAnnouncementCommand.address)!!
        val newPet = Pet(
            name = announcementCommand.name!!,
            lastSeenLocation = makePoint(announcementCommand.lon!!, announcementCommand.lat!!),
            species = SpeciesEnum.valueOf(announcementCommand.species!!.uppercase()),
            breed = announcementCommand.breed!!,
            sex = PetSexEnum.valueOf(announcementCommand.sex!!.uppercase()),
            color = announcementCommand.color,
        ).also {
            it.extraInfo = announcementCommand.additionalInformation
            it.phoneNumber =  announcementCommand.phoneNumber
            it.humanReadableAddress = announcementCommand.address
        }
//        newPet.addToTraceHistory(lastSeenLocation)

        val geofence = repository.save(newPet)
        return one.breece.track_rejoice.commands.PetResponseCommand(geofence.id!!, geofence.species.toString(), false, announcementCommand.name, announcementCommand.breed, announcementCommand.color, announcementCommand.phoneNumber, announcementCommand.address, announcementCommand.lastSeenDate!!, announcementCommand.additionalInformation)
    }

    private fun makePoint(lon: Double, lat: Double) : Point {
        val gf = GeometryFactory()
        val sf = PackedCoordinateSequenceFactory()
        return gf.createPoint(sf.create(doubleArrayOf(lon, lat), 2))
    }

    override fun deleteById(id: Long) {
        repository.deleteById(id)
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

    override fun readById(id: Long): one.breece.track_rejoice.commands.PetResponseCommand? {
        return repository.findById(id).map { petToAbpResponse.convert(it) }.orElse(null)
    }
}