package one.breece.track_rejoice.mapper

import one.breece.track_rejoice.commands.APBResponse
import one.breece.track_rejoice.domain.Pet
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

@Component
class PetToAbpResponse : Converter<Pet, APBResponse> {

    override fun convert(source: Pet): APBResponse? {
        return APBResponse(
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