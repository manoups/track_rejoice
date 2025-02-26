package one.breece.track_rejoice.mapper

import one.breece.track_rejoice.commands.APBResponse
import one.breece.track_rejoice.commands.AddressCommand
import one.breece.track_rejoice.domain.Pet
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

@Component
class PetToAbpResponse : Converter<Pet, APBResponse> {
    override fun convert(source: Pet): APBResponse? {
        return APBResponse(
            source.id!!,
            source.name,
            source.breed,
            source.color,
            AddressCommand(),
            source.lastSeenDate,
            source.extraInformation
        )
    }
}