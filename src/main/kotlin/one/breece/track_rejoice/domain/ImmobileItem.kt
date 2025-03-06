package one.breece.track_rejoice.domain

import jakarta.persistence.Column
import jakarta.persistence.MappedSuperclass
import org.locationtech.jts.geom.MultiPoint

@MappedSuperclass
abstract class ImmobileItem(
    @Column(columnDefinition = "geometry(MultiPoint, 4326)", nullable = false)
    val lastSeenLocation: MultiPoint,
): BeOnTheLookOut()