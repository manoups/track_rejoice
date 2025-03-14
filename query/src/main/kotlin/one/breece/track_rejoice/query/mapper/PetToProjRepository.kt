package one.breece.track_rejoice.query.mapper

import one.breece.track_rejoice.core.projections.BeOnTheLookOutProj
import one.breece.track_rejoice.query.domain.Pet
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

@Component
class PetToProjRepository:Converter<Pet, BeOnTheLookOutProj> {
    override fun convert(source: Pet): BeOnTheLookOutProj? {
        return BeOnTheLookOutProj(
            source.species.toString(),
            source.color,
            source.lastSeenLocation.coordinates.map { arrayOf(it.x, it.y) },
            "Marker",
            source.lastSeenDate,
            source.sku.toString(),
            "/details/pet/${source.sku}"
        )
    }
}