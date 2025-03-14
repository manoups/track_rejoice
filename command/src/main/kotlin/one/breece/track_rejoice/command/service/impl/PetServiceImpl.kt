package one.breece.track_rejoice.command.service.impl

import jakarta.transaction.Transactional
import one.breece.track_rejoice.command.command.PetAnnouncementCommand
import one.breece.track_rejoice.command.command.PetResponseCommand
import one.breece.track_rejoice.command.command.PhotoDescriptor
import one.breece.track_rejoice.command.domain.Pet
import one.breece.track_rejoice.command.repository.PetRepository
import one.breece.track_rejoice.command.service.PetService
import one.breece.track_rejoice.core.domain.PetSexEnum
import one.breece.track_rejoice.core.domain.SpeciesEnum
import one.breece.track_rejoice.core.util.GeometryUtil
import org.apache.commons.io.FilenameUtils
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Service
import java.util.*

@Service
class PetServiceImpl(
    private val repository: PetRepository,
    private val petToPetResponseCommand : Converter<Pet, PetResponseCommand>
) : PetService {
    @Transactional
    override fun createBolo(announcementCommand: PetAnnouncementCommand): PetResponseCommand {
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
        }
//        newPet.addToTraceHistory(lastSeenLocation)

        val pet = repository.save(newPet)
        return petToPetResponseCommand.convert(pet)!!
    }


    override fun deleteById(id: Long) {
        repository.deleteById(id)
    }

    /*@Transactional
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
    }*/


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