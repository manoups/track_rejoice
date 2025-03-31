package one.breece.track_rejoice.command.service.impl

import jakarta.servlet.http.HttpServletRequest
import jakarta.transaction.Transactional
import one.breece.track_rejoice.command.command.BicycleAnnouncementCommand
import one.breece.track_rejoice.command.domain.Bicycle
import one.breece.track_rejoice.command.events.CreateQR
import one.breece.track_rejoice.command.repository.BicycleRepository
import one.breece.track_rejoice.command.service.BicycleService
import one.breece.track_rejoice.core.command.BicycleResponseCommand
import one.breece.track_rejoice.core.util.GeometryUtil
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.ApplicationEventPublisher
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Service
import java.util.*

@Service
class BicycleServiceImpl(
    private val repository: BicycleRepository,
    private val bicycleToBicycleResponseCommand: Converter<Bicycle, BicycleResponseCommand>,
    val applicationEventPublisher: ApplicationEventPublisher
) : BicycleService {

    @Value("\${aws.s3.bucket}")
    lateinit var bucketName: String

    @Transactional
    override fun createBolo(announcementCommand: BicycleAnnouncementCommand, request: HttpServletRequest): BicycleResponseCommand {
        val transportation = Bicycle(
            color = announcementCommand.color!!,
            maker = announcementCommand.maker!!,
            model = announcementCommand.model!!,
            year = announcementCommand.year!!,
            phoneNumber = announcementCommand.phoneNumber!!,
            lastSeenLocation = GeometryUtil.parseLocation(announcementCommand.lon!!, announcementCommand.lat!!),
        ).also {
            it.lastSeenDate = announcementCommand.lastSeenDate!!
            it.extraInfo = announcementCommand.additionalInformation
        }
        val bicycle = repository.save(transportation)
        val host = request.requestURL.toString().replace(request.requestURI, "")
        applicationEventPublisher.publishEvent(CreateQR("$host/details/bike/${bicycle.sku}", bucketName, "qr-code/${bicycle.sku}.png", bicycle.id!!))
        return bicycleToBicycleResponseCommand.convert(bicycle)!!
    }

    @Transactional
    override fun readBySku(sku: UUID): BicycleResponseCommand {
        return repository.findBySku(sku).map { bicycleToBicycleResponseCommand.convert(it)!! }
            .orElseThrow { RuntimeException("Bicycle with sku=$sku not found") }
    }
}