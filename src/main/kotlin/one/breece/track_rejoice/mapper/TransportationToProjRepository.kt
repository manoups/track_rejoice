package one.breece.track_rejoice.mapper

import one.breece.track_rejoice.domain.MeansOfTransportation
import one.breece.track_rejoice.repository.projections.BeOnTheLookOutProj
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

@Component
class TransportationToProjRepository : Converter<MeansOfTransportation, BeOnTheLookOutProj> {
    override fun convert(source: MeansOfTransportation): BeOnTheLookOutProj? {
        return BeOnTheLookOutProj(
            "${source.model} ${source.maker}",
            source.color,
            source.lastSeenLocation.coordinates.map { arrayOf(it.x, it.y) },
            "Marker",
            source.lastSeenDate
        )
    }
}