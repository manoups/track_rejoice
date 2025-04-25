package one.breece.track_rejoice.command.mapper

import one.breece.track_rejoice.command.domain.Pet
import one.breece.track_rejoice.core.projections.BeOnTheLookOutProj
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

@Component
class PetToProjCommandRepository:Converter<Pet, BeOnTheLookOutProj> {
    override fun convert(source: Pet): BeOnTheLookOutProj? {
        return BeOnTheLookOutProj(
            source.species.toString(),
            source.color,
            source.lastSeenLocation.coordinates.map { arrayOf(it.x, it.y) },
            "Marker",
            source.lastSeenDate,
            source.sku.toString(),
            source.state.toString(),
            "/details/pet/${source.sku}"
        )
    }
}