package one.breece.track_rejoice.command.service.impl

import jakarta.servlet.http.HttpServletRequest
import jakarta.transaction.Transactional
import one.breece.track_rejoice.command.command.PetAnnouncementCommand
import one.breece.track_rejoice.command.domain.Pet
import one.breece.track_rejoice.command.events.CreateQR
import one.breece.track_rejoice.command.repository.PetRepository
import one.breece.track_rejoice.command.service.PetService
import one.breece.track_rejoice.core.command.PetResponseCommand
import one.breece.track_rejoice.core.domain.PetSexEnum
import one.breece.track_rejoice.core.domain.SpeciesEnum
import one.breece.track_rejoice.core.util.GeometryUtil
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.ApplicationEventPublisher
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Service
import java.util.*

@Service
class PetServiceImpl(
    private val repository: PetRepository,
    private val petToPetResponseCommand: Converter<Pet, PetResponseCommand>,
    private val applicationEventPublisher: ApplicationEventPublisher
) : PetService {
    @Value("\${aws.s3.bucket}")
    lateinit var bucketName: String


    @Transactional
    override fun createBolo(
        announcementCommand: PetAnnouncementCommand,
        request: HttpServletRequest
    ): PetResponseCommand {
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
        val host = request.requestURL.toString().replace(request.requestURI, "")
        applicationEventPublisher.publishEvent(
            CreateQR(
                "$host/details/pet/${pet.sku}",
                bucketName,
                "qr-code/${pet.sku}.png",
                pet.id!!
            )
        )
        return petToPetResponseCommand.convert(pet)!!
    }

    @Transactional
    override fun readBySku(sku: UUID): PetResponseCommand {
        return repository.findBySku(sku).map { petToPetResponseCommand.convert(it)!! }
            .orElseThrow { RuntimeException("Pet with sku=$sku not found") }
    }
}