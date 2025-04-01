package one.breece.track_rejoice.command.mapper

import one.breece.track_rejoice.command.domain.Bicycle
import one.breece.track_rejoice.core.command.BicycleResponseCommand
import one.breece.track_rejoice.core.command.PhotoDescriptor
import one.breece.track_rejoice.core.domain.BoloStates
import org.apache.commons.io.FilenameUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

@Component
class BicycleToBicycleResponseCommand : Converter<Bicycle, BicycleResponseCommand> {
    @Value("\${spring.cloud.azure.storage.blob.endpoint}")
    lateinit var endpoint: String

    override fun convert(source: Bicycle): BicycleResponseCommand {
        return BicycleResponseCommand(
            source.id!!,
            source.state == BoloStates.ACTIVE,
            source.color,
            source.maker,
            source.model,
            source.year,
            source.phoneNumber,
            source.lastSeenDate,
            source.extraInfo,
            source.lastSeenLocation.y,
            source.lastSeenLocation.x,
            source.sku,
            source.photo.map {
                PhotoDescriptor(
                    "$endpoint${it.bucket}/${it.key}", FilenameUtils.removeExtension(
                        FilenameUtils.getName(it.key)
                    )
                )
            }, source.qrCodeKey
        )
    }
}