package one.breece.track_rejoice.query.mapper


import one.breece.track_rejoice.core.projections.BeOnTheLookOutProj
import one.breece.track_rejoice.query.domain.Bicycle
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

@Component
class BicycleToProjRepository : Converter<Bicycle, BeOnTheLookOutProj> {
    override fun convert(source: Bicycle): BeOnTheLookOutProj? {
        return BeOnTheLookOutProj(
            "${source.model} ${source.maker}",
            source.color,
            source.lastSeenLocation.coordinates.map { arrayOf(it.x, it.y) },
            "Marker",
            source.lastSeenDate,
            source.sku.toString()
        )
    }
}