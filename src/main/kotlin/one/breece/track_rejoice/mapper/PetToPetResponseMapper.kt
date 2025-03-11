package one.breece.track_rejoice.mapper

import one.breece.track_rejoice.domain.command.Pet
import one.breece.track_rejoice.web.dto.PetResponse
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

@Component
class PetToPetResponseMapper: Converter<Pet, PetResponse> {
    override fun convert(source: Pet): PetResponse {
        return PetResponse(
            id = source.id!!,
            name = source.name,
            species = source.species.toString(),
//            lastSeenLocation = source.lastSeenLocation,
            lastSeenDate = source.lastSeenDate,
            longitude = source.lastSeenLocation.x,
            latitude =  source.lastSeenLocation.y,
            humanReadableAddress = source.humanReadableAddress
//            traceHistory = source.traceHistory.map { mapper.convert(it) }
        )
    }

}