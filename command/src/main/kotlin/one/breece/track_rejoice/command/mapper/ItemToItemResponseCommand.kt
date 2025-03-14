package one.breece.track_rejoice.command.mapper

import one.breece.track_rejoice.command.command.ItemResponseCommand
import one.breece.track_rejoice.command.domain.Item
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

@Component
class ItemToItemResponseCommand:Converter<Item, ItemResponseCommand> {
    override fun convert(geofence: Item): ItemResponseCommand {
        return ItemResponseCommand(
            geofence.id!!,
            geofence.enabled,
            geofence.shortDescription,
            geofence.color,
            geofence.phoneNumber,
            geofence.lastSeenDate,
            geofence.extraInfo,
            geofence.lastSeenLocation.coordinates.map { doubleArrayOf(it.x, it.y) },
            geofence.sku
        )
    }
}