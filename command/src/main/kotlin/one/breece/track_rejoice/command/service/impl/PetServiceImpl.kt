package one.breece.track_rejoice.command.service.impl

import jakarta.transaction.Transactional
import one.breece.track_rejoice.command.command.PetAnnouncementCommand
import one.breece.track_rejoice.command.domain.Pet
import one.breece.track_rejoice.command.repository.PetRepository
import one.breece.track_rejoice.command.service.PetService
import one.breece.track_rejoice.core.command.PetResponseCommand
import one.breece.track_rejoice.core.domain.PetSexEnum
import one.breece.track_rejoice.core.domain.SpeciesEnum
import one.breece.track_rejoice.core.util.GeometryUtil
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

    override fun readBySku(sku: UUID): PetResponseCommand {
        repository.findBySku(sku).let { optional ->
            return if (optional.isPresent) {
                val pet = optional.get()
                petToPetResponseCommand.convert(pet)!!
            } else {
                throw RuntimeException("Pet with sku=$sku not found")
            }
        }
    }
}