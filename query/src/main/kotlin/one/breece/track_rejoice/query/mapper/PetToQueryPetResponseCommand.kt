package one.breece.track_rejoice.query.mapper

import one.breece.track_rejoice.core.command.PetResponseCommand
import one.breece.track_rejoice.core.command.PhotoDescriptor
import one.breece.track_rejoice.core.domain.BoloStates
import one.breece.track_rejoice.query.domain.Pet
import org.apache.commons.io.FilenameUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component
import java.nio.file.Paths

@Component
class PetToQueryPetResponseCommand : Converter<Pet, PetResponseCommand> {
    @Value("\${spring.cloud.azure.storage.blob.endpoint}")
    lateinit var endpoint: String

    override fun convert(source: Pet): PetResponseCommand {
        return PetResponseCommand(
            source.id!!,
            source.species.toString(),
            source.state == BoloStates.ACTIVE,
            source.name,
            source.breed,
            source.color,
            source.phoneNumber,
            source.lastSeenDate,
            source.extraInfo,
            source.sex.toString(),
            source.lastSeenLocation.coordinates.first().y,
            source.lastSeenLocation.coordinates.first().x,
            source.sku,
            source.photo.map {
                PhotoDescriptor(
                    Paths.get(endpoint, it.bucket, it.key).toString(), FilenameUtils.removeExtension(
                        FilenameUtils.getName(it.key)
                    )
                )
            }, source.qrCodeKey
        )
    }
}