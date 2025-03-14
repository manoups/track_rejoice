package one.breece.track_rejoice.query.domain

import jakarta.persistence.Entity
import jakarta.persistence.Inheritance
import jakarta.persistence.InheritanceType
import one.breece.track_rejoice.core.domain.BeOnTheLookoutCore
import org.locationtech.jts.geom.Geometry

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
open class BeOnTheLookOut(phoneNumber: String, lastSeenLocation: Geometry) :
    BeOnTheLookoutCore(phoneNumber, lastSeenLocation = lastSeenLocation) {
}