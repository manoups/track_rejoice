package one.breece.track_rejoice.mapper

import one.breece.track_rejoice.commands.PetResponseCommand
import one.breece.track_rejoice.domain.command.Pet
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

@Component
class PetToAbpResponse : Converter<Pet, PetResponseCommand> {

    override fun convert(source: Pet): PetResponseCommand? {
        return PetResponseCommand(
            source.id!!,
            source.species.toString(),
            source.enabled,
            source.name,
            source.breed,
            source.color,
            source.phoneNumber,
            source.humanReadableAddress,
            source.lastSeenDate,
            source.extraInfo,
            source.sex.toString(),
            source.lastSeenLocation.y,
            source.lastSeenLocation.x
        )
    }
}