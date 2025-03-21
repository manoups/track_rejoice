package one.breece.track_rejoice.command.domain

import jakarta.persistence.*
import jakarta.validation.constraints.PositiveOrZero
import one.breece.track_rejoice.core.domain.BeOnTheLookoutCore
import org.hibernate.annotations.TenantId
import org.locationtech.jts.geom.Geometry

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
abstract class BeOnTheLookOut(
    phoneNumber: String, lastSeenLocation: Geometry,
    @OneToMany(
        fetch = FetchType.LAZY,
        cascade = [CascadeType.ALL],
        orphanRemoval = true,
        mappedBy = "beOnTheLookOut"
    )
    val traceHistory: MutableSet<Trace> = HashSet(),
    @TenantId
    @PositiveOrZero
    var createdBy: Long? = null
) : BeOnTheLookoutCore(phoneNumber, lastSeenLocation = lastSeenLocation)
//    We should not store the trace as a polygon because we want to track timestamps
//    @Column(name = "geometry_polygon", columnDefinition = "geometry(Polygon, 4326)", nullable = false)
//    var geometryPolygon: Polygon
{
    @Transient
    fun addToTraceHistory(location: Geometry) {
        val trace = Trace(location = location, beOnTheLookOut = this)
        traceHistory.add(trace)
    }
}
