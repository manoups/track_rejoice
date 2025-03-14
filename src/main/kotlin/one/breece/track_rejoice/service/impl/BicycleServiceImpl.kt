package one.breece.track_rejoice.service.impl

import one.breece.track_rejoice.commands.BicycleAnnouncementCommand
import one.breece.track_rejoice.commands.BicycleResponseCommand
import one.breece.track_rejoice.core.util.GeometryUtil
import one.breece.track_rejoice.domain.command.Bicycle
import one.breece.track_rejoice.repository.command.BicycleRepository
import one.breece.track_rejoice.service.BicycleService
import one.breece.track_rejoice.web.dto.PhotoDescriptor
import org.apache.commons.io.FilenameUtils
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Service
import java.lang.RuntimeException
import java.util.*

@Service
class BicycleServiceImpl(
    private val repository: BicycleRepository,
    private val bicycleMapper: Converter<Bicycle, BicycleResponseCommand>
) : BicycleService {
    override fun createAPB(announcementCommand: BicycleAnnouncementCommand): BicycleResponseCommand {

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
        return bicycleMapper.convert(repository.save(transportation))!!
    }

    override fun deleteById(id: Long) {
        repository.deleteById(id)
    }

    override fun readById(id: Long): BicycleResponseCommand? {
        return repository.findById(id).map { bicycleMapper.convert(it) }.orElse(null)
    }

    override fun readBySku(sku: UUID): BicycleResponseCommand {
        repository.findBySku(sku).let { optional ->
            return if (optional.isPresent) {
                val item = optional.get()
                BicycleResponseCommand(
                    item.id!!,
                    item.enabled,
                    item.color,
                    item.maker,
                    item.model,
                    item.year,
                    item.phoneNumber,
                    item.lastSeenDate,
                    item.extraInfo,
                    item.lastSeenLocation.coordinates.first().y,
                    item.lastSeenLocation.coordinates.first().x,
                    item.sku,
                    item.photo.map {
                        PhotoDescriptor(
                            "https://${it.bucket}.s3.amazonaws.com/${it.key}", FilenameUtils.removeExtension(
                                FilenameUtils.getName(it.key)
                            )
                        )
                    }
                )
            } else {
                throw RuntimeException("Bicycle with sku=$sku not found")
            }
        }
    }
}