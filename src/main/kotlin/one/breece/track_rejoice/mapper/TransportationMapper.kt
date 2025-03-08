package one.breece.track_rejoice.mapper

import one.breece.track_rejoice.commands.MeansOfTransportationResponseCommand
import one.breece.track_rejoice.domain.MeansOfTransportation
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

@Component
class TransportationMapper: Converter<MeansOfTransportation, MeansOfTransportationResponseCommand> {
    override fun convert(source: MeansOfTransportation): MeansOfTransportationResponseCommand {
        return MeansOfTransportationResponseCommand(
            source.id!!,
            source.color,
            source.maker,
            source.model,
            source.plate,
            source.year,
            source.phoneNumber,
            source.lastSeenDate,
            source.extraInfo,
            source.lastSeenLocation.y,
            source.lastSeenLocation.x
        )
    }
}