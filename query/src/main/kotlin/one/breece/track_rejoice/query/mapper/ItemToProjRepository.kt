package one.breece.track_rejoice.query.mapper

import one.breece.track_rejoice.core.projections.BeOnTheLookOutProj
import one.breece.track_rejoice.query.domain.Item
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

@Component
class ItemToProjRepository:Converter<Item, BeOnTheLookOutProj> {
    override fun convert(source: Item): BeOnTheLookOutProj? {
        return BeOnTheLookOutProj(
            source.shortDescription,
            source.color,
            source.lastSeenLocation.coordinates.map { doubleArrayOf(it.y, it.x) },
            "MultiPoint",
            source.lastSeenDate,
            source.sku.toString(),
            "/details/item/${source.sku}"
        )
    }
}