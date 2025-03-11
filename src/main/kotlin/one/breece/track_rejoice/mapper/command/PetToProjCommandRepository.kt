package one.breece.track_rejoice.mapper.command

import one.breece.track_rejoice.domain.command.Pet
import one.breece.track_rejoice.repository.projections.BeOnTheLookOutProj
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
            source.sku.toString()
        )
    }
}