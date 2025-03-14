package one.breece.track_rejoice.query.domain

import jakarta.persistence.MappedSuperclass
import org.locationtech.jts.geom.MultiPoint

@MappedSuperclass
abstract class ImmobileItem(
    phoneNumber: String,
    lastSeenLocation: MultiPoint,
): BeOnTheLookOut(phoneNumber, lastSeenLocation = lastSeenLocation) {
    override val lastSeenLocation: MultiPoint
        get() = super.lastSeenLocation as MultiPoint
}