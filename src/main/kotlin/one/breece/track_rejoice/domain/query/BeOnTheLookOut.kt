package one.breece.track_rejoice.domain.query

import jakarta.persistence.Entity
import jakarta.persistence.Inheritance
import jakarta.persistence.InheritanceType
import one.breece.track_rejoice.domain.BeOnTheLookoutCore
import org.locationtech.jts.geom.Geometry

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
class BeOnTheLookOut(phoneNumber: String, lastSeenLocation: Geometry) :
    BeOnTheLookoutCore(phoneNumber, lastSeenLocation = lastSeenLocation) {
}