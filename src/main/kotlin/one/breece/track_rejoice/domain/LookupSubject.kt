package one.breece.track_rejoice.domain

import jakarta.annotation.Nonnull
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.locationtech.jts.geom.Point
import java.util.*

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
abstract class LookupSubject(
//    We should not store the trace as a polygon because we want to track timestamps
//    @Column(name = "geometry_polygon", columnDefinition = "geometry(Polygon, 4326)", nullable = false)
//    var geometryPolygon: Polygon,
    @Column(columnDefinition = "geometry(Point, 4326)", nullable = false)
    val lastSeenLocation: Point,
    @Nonnull
    val lastSeenDate: Date = Date(),
    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval =  true, mappedBy="lookupSubject")
    val traceHistory: MutableSet<Trace> =  HashSet(),
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long? = null,
    @Version val version: Short = 0,
    @CreationTimestamp val createdAt: Date? = null,
    @UpdateTimestamp val updatedAt: Date? = null,
)
