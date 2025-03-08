package one.breece.track_rejoice.mapper

import one.breece.track_rejoice.domain.Item
import one.breece.track_rejoice.repository.projections.BeOnTheLookOutProj
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

@Component
class ItemToProjRepository:Converter<Item, BeOnTheLookOutProj> {
    override fun convert(source: Item): BeOnTheLookOutProj? {
        return BeOnTheLookOutProj(
            null,
            source.shortDescription,
            source.color,
            source.lastSeenLocation.coordinates.map { doubleArrayOf(it.y, it.x) },
            "MultiPoint",
            source.lastSeenDate
        )
    }
}