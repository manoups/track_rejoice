package one.breece.track_rejoice.domain

import jakarta.persistence.Column
import jakarta.persistence.MappedSuperclass
import one.breece.track_rejoice.commands.AddressCommand
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import org.locationtech.jts.geom.Point

@MappedSuperclass
abstract class MobileItem(
    @Column(columnDefinition = "geometry(Point, 4326)", nullable = false)
    val lastSeenLocation: Point,
    @JdbcTypeCode(SqlTypes.JSON)
    var humanReadableAddress: AddressCommand? = null) : BeOnTheLookOut()