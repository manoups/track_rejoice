package one.breece.track_rejoice.query.mapper

import one.breece.track_rejoice.core.command.BicycleResponseCommand
import one.breece.track_rejoice.core.command.PhotoDescriptor
import one.breece.track_rejoice.core.domain.BoloStates
import one.breece.track_rejoice.query.domain.Bicycle
import org.apache.commons.io.FilenameUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component
import java.nio.file.Paths

@Component
class BicycleToQueryBicycleResponseCommand  : Converter<Bicycle, BicycleResponseCommand> {
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
                    Paths.get(endpoint, it.bucket, it.key).toString(), FilenameUtils.removeExtension(
                        FilenameUtils.getName(it.key)
                    )
                )
            },
            source.qrCodeKey
        )
    }
}