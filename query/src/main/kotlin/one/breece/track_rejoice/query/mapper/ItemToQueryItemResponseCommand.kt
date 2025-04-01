package one.breece.track_rejoice.query.mapper

import one.breece.track_rejoice.core.command.ItemResponseCommand
import one.breece.track_rejoice.core.command.PhotoDescriptor
import one.breece.track_rejoice.core.domain.BoloStates
import one.breece.track_rejoice.query.domain.Item
import org.apache.commons.io.FilenameUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

@Component
class ItemToQueryItemResponseCommand : Converter<Item, ItemResponseCommand> {
    @Value("\${spring.cloud.azure.storage.blob.endpoint}")
    lateinit var endpoint: String
    override fun convert(source: Item): ItemResponseCommand {
        return ItemResponseCommand(
            source.id!!,
            source.state == BoloStates.ACTIVE,
            source.shortDescription,
            source.phoneNumber,
            source.color,
            source.lastSeenDate,
            source.extraInfo,
            source.lastSeenLocation.coordinates.map { doubleArrayOf(it.y, it.x) },
            source.sku,
            source.photo.map {
                PhotoDescriptor(
                    "$endpoint${it.bucket}/${it.key}", FilenameUtils.removeExtension(FilenameUtils.getName(it.key)))
            }, source.qrCodeKey)
    }
}