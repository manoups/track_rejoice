package one.breece.track_rejoice.mapper.command

import one.breece.track_rejoice.domain.command.Item
import one.breece.track_rejoice.repository.projections.BeOnTheLookOutProj
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

@Component
class ItemToProjCommandRepository:Converter<Item, BeOnTheLookOutProj> {
    override fun convert(source: Item): BeOnTheLookOutProj? {
        return BeOnTheLookOutProj(
            source.shortDescription,
            source.color,
            source.lastSeenLocation.coordinates.map { doubleArrayOf(it.y, it.x) },
            "MultiPoint",
            source.lastSeenDate,
            source.sku.toString()
        )
    }
}