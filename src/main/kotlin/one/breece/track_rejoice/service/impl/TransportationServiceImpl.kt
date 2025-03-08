package one.breece.track_rejoice.service.impl

import one.breece.track_rejoice.commands.MeansOfTransportationAnnouncementCommand
import one.breece.track_rejoice.commands.MeansOfTransportationResponseCommand
import one.breece.track_rejoice.domain.MeansOfTransportation
import one.breece.track_rejoice.repository.TransportationRepository
import one.breece.track_rejoice.service.TransportationService
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Service

@Service
class TransportationServiceImpl(
    private val repository: TransportationRepository,
    private val mobileUtilService: MobileUtilService,
    private val transportationMapper: Converter<MeansOfTransportation, MeansOfTransportationResponseCommand>
) : TransportationService {
    override fun createAPB(announcementCommand: MeansOfTransportationAnnouncementCommand): MeansOfTransportationResponseCommand {

        val transportation = MeansOfTransportation(
            color = announcementCommand.color!!,
            maker = announcementCommand.maker!!,
            model = announcementCommand.model!!,
            year = announcementCommand.year!!,
            plate = announcementCommand.plate!!,
            phoneNumber = announcementCommand.phoneNumber!!,
            lastSeenLocation = mobileUtilService.makePoint(announcementCommand.lon!!, announcementCommand.lat!!),
        ).also {
            it.lastSeenDate = announcementCommand.lastSeenDate!!
            it.extraInfo = announcementCommand.additionalInformation
        }
        return transportationMapper.convert(repository.save(transportation))!!
    }

    override fun deleteById(id: Long) {
        repository.deleteById(id)
    }

    override fun readById(id: Long): MeansOfTransportationResponseCommand? {
        return repository.findById(id).map { transportationMapper.convert(it) }.orElse(null)
    }
}