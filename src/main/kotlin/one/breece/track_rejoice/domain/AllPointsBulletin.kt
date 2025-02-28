package one.breece.track_rejoice.domain

import jakarta.annotation.Nonnull
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.TenantId
import org.hibernate.annotations.UpdateTimestamp
import org.locationtech.jts.geom.Geometry
import java.time.LocalDateTime
import java.util.*

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
abstract class AllPointsBulletin(
//    We should not store the trace as a polygon because we want to track timestamps
//    @Column(name = "geometry_polygon", columnDefinition = "geometry(Polygon, 4326)", nullable = false)
//    var geometryPolygon: Polygon
    @Pattern(regexp = "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$")
    var phoneNumber: String? = null,
    @Nonnull
    val lastSeenDate: LocalDateTime = LocalDateTime.now(),

    @OneToMany(
        fetch = FetchType.LAZY,
        cascade = [CascadeType.ALL],
        orphanRemoval = true,
        mappedBy = "allPointsBulletin"
    )
    val traceHistory: MutableSet<Trace> = HashSet(),
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Version
    val version: Short = 0,
    @CreationTimestamp
    val createdAt: Date? = null,
    @UpdateTimestamp
    val updatedAt: Date? = null,

    @TenantId
    var createdBy: Long? = null,
    @Column(columnDefinition =  "varchar(1024)")
    var extraInfo: String? = null,

    @NotNull
    @Column(columnDefinition = "boolean default false")
    var enabled: Boolean = false
) {
    @Transient
    fun addToTraceHistory(location: Geometry) {
        val trace = Trace(location = location, allPointsBulletin = this)
        traceHistory.add(trace)
    }
}
