package one.breece.track_rejoice.service.impl

import one.breece.track_rejoice.commands.BicycleAnnouncementCommand
import one.breece.track_rejoice.commands.BicycleResponseCommand
import one.breece.track_rejoice.domain.Bicycle
import one.breece.track_rejoice.repository.BicycleRepository
import one.breece.track_rejoice.service.BicycleService
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Service

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
}