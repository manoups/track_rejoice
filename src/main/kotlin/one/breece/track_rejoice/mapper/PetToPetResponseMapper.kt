package one.breece.track_rejoice.mapper

import one.breece.track_rejoice.domain.Pet
import one.breece.track_rejoice.web.dto.PetResponse
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

@Component
class PetToPetResponseMapper: Converter<Pet, PetResponse> {
    override fun convert(source: Pet): PetResponse {
        return PetResponse(
            id = source.id!!,
            name = source.name,
//            lastSeenLocation = source.lastSeenLocation,
            lastSeenDate = source.lastSeenDate,
            longitude = source.lastSeenLocation.x,
            latitude =  source.lastSeenLocation.y
//            ,
//            traceHistory = source.traceHistory.map { mapper.convert(it) }
        )
    }

}