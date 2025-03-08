package one.breece.track_rejoice.domain

import jakarta.annotation.Nonnull
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import org.locationtech.jts.geom.Geometry
import java.time.LocalDateTime
import java.util.*

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
abstract class BeOnTheLookOut(
//    We should not store the trace as a polygon because we want to track timestamps
//    @Column(name = "geometry_polygon", columnDefinition = "geometry(Polygon, 4326)", nullable = false)
//    var geometryPolygon: Polygon
    @Pattern(regexp = "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$")
    var phoneNumber: String,
    @Nonnull
    var lastSeenDate: LocalDateTime = LocalDateTime.now(),

    @OneToMany(
        fetch = FetchType.LAZY,
        cascade = [CascadeType.ALL],
        orphanRemoval = true,
        mappedBy = "beOnTheLookOut"
    )
    val traceHistory: MutableSet<Trace> = HashSet(),

    @Column(columnDefinition =  "varchar(1024)")
    var extraInfo: String? = null,
    @Column(unique = true, nullable=false, updatable = false)
    val sku: UUID = UUID.randomUUID(),
    @NotNull
    @Column(columnDefinition = "boolean default false")
    var enabled: Boolean = false
):BoilerPlate() {
    @Transient
    fun addToTraceHistory(location: Geometry) {
        val trace = Trace(location = location, beOnTheLookOut = this)
        traceHistory.add(trace)
    }
}
