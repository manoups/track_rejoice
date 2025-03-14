package one.breece.track_rejoice.command.mapper

import one.breece.track_rejoice.command.command.PetResponseCommand
import one.breece.track_rejoice.command.domain.Pet
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

@Component
class PetToPetResponseCommand : Converter<Pet, PetResponseCommand> {
    override fun convert(source: Pet): PetResponseCommand? {
        return PetResponseCommand(
            source.id!!,
            source.species.toString(),
            source.enabled,
            source.name,
            source.breed,
            source.color,
            source.phoneNumber,
            source.lastSeenDate,
            source.extraInfo,
            sku = source.sku
        )
    }
}