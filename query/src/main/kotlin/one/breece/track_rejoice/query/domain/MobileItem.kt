package one.breece.track_rejoice.query.domain

import jakarta.persistence.MappedSuperclass
import org.locationtech.jts.geom.Point

@MappedSuperclass
abstract class MobileItem(
    phoneNumber: String,
    lastSeenLocation: Point
) : BeOnTheLookOut(phoneNumber, lastSeenLocation = lastSeenLocation) {
    override val lastSeenLocation: Point
        get() = super.lastSeenLocation as Point

    }