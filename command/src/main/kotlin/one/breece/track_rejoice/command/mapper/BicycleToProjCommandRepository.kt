package one.breece.track_rejoice.command.mapper

import one.breece.track_rejoice.command.domain.Bicycle
import one.breece.track_rejoice.core.projections.BeOnTheLookOutProj
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

@Component
class BicycleToProjCommandRepository : Converter<Bicycle, BeOnTheLookOutProj> {
    override fun convert(source: Bicycle): BeOnTheLookOutProj? {
        return BeOnTheLookOutProj(
            "${source.model} ${source.maker}",
            source.color,
            source.lastSeenLocation.coordinates.map { arrayOf(it.x, it.y) },
            "Marker",
            source.lastSeenDate,
            source.sku.toString(),
            "/details/bike/${source.sku}"
        )
    }
}