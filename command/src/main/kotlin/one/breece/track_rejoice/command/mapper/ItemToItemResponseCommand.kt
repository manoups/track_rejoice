package one.breece.track_rejoice.command.mapper

import one.breece.track_rejoice.core.command.ItemResponseCommand
import one.breece.track_rejoice.command.domain.Item
import one.breece.track_rejoice.core.command.PhotoDescriptor
import one.breece.track_rejoice.core.domain.BoloStateEnum
import org.apache.commons.io.FilenameUtils
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

@Component
class ItemToItemResponseCommand : Converter<Item, ItemResponseCommand> {
    override fun convert(source: Item): ItemResponseCommand {
        return ItemResponseCommand(
            source.id!!,
            source.state == BoloStateEnum.ACTIVE,
            source.shortDescription,
            source.color,
            source.phoneNumber,
            source.lastSeenDate,
            source.extraInfo,
            source.lastSeenLocation.coordinates.map { doubleArrayOf(it.x, it.y) },
            source.sku,
            source.photo.map {
                PhotoDescriptor(
                    "https://${it.bucket}.s3.amazonaws.com/${it.key}",
                    FilenameUtils.getName(it.key)
                )
            }, source.qrCodeKey)
    }
}