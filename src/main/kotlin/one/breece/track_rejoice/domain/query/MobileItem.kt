package one.breece.track_rejoice.domain.query

import jakarta.persistence.MappedSuperclass
import one.breece.track_rejoice.commands.AddressCommand
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import org.locationtech.jts.geom.Point

@MappedSuperclass
abstract class MobileItem(
    phoneNumber: String,
    lastSeenLocation: Point,
    @JdbcTypeCode(SqlTypes.JSON)
    var humanReadableAddress: AddressCommand? = null) : BeOnTheLookOut(phoneNumber, lastSeenLocation = lastSeenLocation) {
    override val lastSeenLocation: Point
        get() = super.lastSeenLocation as Point

    }