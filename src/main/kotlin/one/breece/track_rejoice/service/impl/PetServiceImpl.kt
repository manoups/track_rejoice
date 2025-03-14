package one.breece.track_rejoice.service.impl

import jakarta.transaction.Transactional
import one.breece.track_rejoice.commands.PetAnnouncementCommand
import one.breece.track_rejoice.commands.PetResponseCommand
import one.breece.track_rejoice.core.domain.PetSexEnum
import one.breece.track_rejoice.core.domain.SpeciesEnum
import one.breece.track_rejoice.core.util.GeometryUtil
import one.breece.track_rejoice.domain.command.Pet
import one.breece.track_rejoice.repository.command.PetRepository
import one.breece.track_rejoice.service.PetService
import one.breece.track_rejoice.web.dto.PetResponse
import one.breece.track_rejoice.web.dto.PhotoDescriptor
import org.apache.commons.io.FilenameUtils
import org.springframework.core.convert.converter.Converter
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*

@Service
class PetServiceImpl(
    private val repository: PetRepository,
    private val petToPetResponseMapper: Converter<Pet, PetResponse>,
    private val petToAbpResponse: Converter<Pet, PetResponseCommand>
) : PetService {
    @Transactional
    override fun createAPB(announcementCommand: PetAnnouncementCommand): PetResponseCommand {
//        val lastSeenLocation = geocodingService.geocode(petAnnouncementCommand.address)!!
        val newPet = Pet(
            name = announcementCommand.name!!,
            lastSeenLocation = GeometryUtil.parseLocation(announcementCommand.lon!!, announcementCommand.lat!!),
            species = SpeciesEnum.valueOf(announcementCommand.species!!.uppercase()),
            breed = announcementCommand.breed!!,
            sex = PetSexEnum.valueOf(announcementCommand.sex!!.uppercase()),
            phoneNumber = announcementCommand.phoneNumber!!,
            color = announcementCommand.color,
        ).also {
            it.lastSeenDate = announcementCommand.lastSeenDate!!
            it.extraInfo = announcementCommand.additionalInformation
            it.humanReadableAddress = announcementCommand.address
        }
//        newPet.addToTraceHistory(lastSeenLocation)

        val geofence = repository.save(newPet)
        return PetResponseCommand(
            geofence.id!!,
            geofence.species.toString(),
            false,
            announcementCommand.name,
            announcementCommand.breed,
            announcementCommand.color,
            announcementCommand.phoneNumber,
            announcementCommand.address,
            announcementCommand.lastSeenDate!!,
            announcementCommand.additionalInformation,
            sku = geofence.sku
        )
    }


    override fun deleteById(id: Long) {
        repository.deleteById(id)
    }

    @Transactional
    override fun findAllByLngLat(
        lon: Double,
        lat: Double,
        distanceInMeters: Double,
        pageRequest: Pageable
    ): Page<PetResponse> {
        return repository.findAllByLngLat(lon, lat, distanceInMeters, pageRequest)
            .map { petToPetResponseMapper.convert(it)!! }
    }

    override fun findById(petId: Long): Optional<PetResponse> {
        return repository.findById(petId).map { petToPetResponseMapper.convert(it) }
    }

    override fun findDistanceBetween(id1: Long, id2: Long) = repository.findDistanceBetween(id1, id2)

    override fun findAll(): List<PetResponse> {
        return repository.findAll().map { petToPetResponseMapper.convert(it)!! }
    }

    override fun readById(id: Long): PetResponseCommand? {
        return repository.findById(id).map { petToAbpResponse.convert(it) }.orElse(null)
    }

    override fun readBySku(sku: UUID): PetResponseCommand {
        repository.findBySku(sku).let { optional ->
            return if (optional.isPresent) {
                val item = optional.get()
                PetResponseCommand(
                    item.id!!,
                    item.species.toString(),
                    item.enabled,
                    item.name,
                    item.breed,
                    item.color,
                    item.phoneNumber,
                    item.humanReadableAddress,
                    item.lastSeenDate,
                    item.extraInfo,
                    item.sex.toString(),
                    item.lastSeenLocation.coordinates.first().y,
                    item.lastSeenLocation.coordinates.first().x,
                    item.sku,
                    item.photo.map {
                        PhotoDescriptor(
                            "https://${it.bucket}.s3.amazonaws.com/${it.key}", FilenameUtils.removeExtension(
                                FilenameUtils.getName(it.key)
                            )
                        )
                    })
            } else {
                throw RuntimeException("Pet with sku=$sku not found")
            }
        }
    }
}