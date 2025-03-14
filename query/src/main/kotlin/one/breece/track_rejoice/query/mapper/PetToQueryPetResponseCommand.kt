package one.breece.track_rejoice.query.mapper

import one.breece.track_rejoice.core.command.PetResponseCommand
import one.breece.track_rejoice.core.command.PhotoDescriptor
import one.breece.track_rejoice.core.domain.BoloStateEnum
import one.breece.track_rejoice.query.domain.Pet
import org.apache.commons.io.FilenameUtils
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

@Component
class PetToQueryPetResponseCommand: Converter<Pet, PetResponseCommand> {
    override fun convert(source: Pet): PetResponseCommand {
        return PetResponseCommand(
            source.id!!,
            source.species.toString(),
            source.state == BoloStateEnum.ACTIVE,
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
                    "https://${it.bucket}.s3.amazonaws.com/${it.key}", FilenameUtils.removeExtension(
                        FilenameUtils.getName(it.key)
                    )
                )
            })
    }
}