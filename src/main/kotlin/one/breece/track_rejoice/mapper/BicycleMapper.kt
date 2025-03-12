package one.breece.track_rejoice.mapper

import one.breece.track_rejoice.commands.BicycleResponseCommand
import one.breece.track_rejoice.domain.command.Bicycle
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

@Component
class BicycleMapper: Converter<Bicycle, BicycleResponseCommand> {
    override fun convert(source: Bicycle): BicycleResponseCommand {
        return BicycleResponseCommand(
            source.id!!,
            source.enabled,
            source.color,
            source.maker,
            source.model,
            source.year,
            source.phoneNumber,
            source.lastSeenDate,
            source.extraInfo,
            source.lastSeenLocation.y,
            source.lastSeenLocation.x,
            source.sku
        )
    }
}