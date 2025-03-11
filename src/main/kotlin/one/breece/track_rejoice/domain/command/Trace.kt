package one.breece.track_rejoice.domain.command

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.locationtech.jts.geom.Geometry
import java.util.*

@Entity
class Trace(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long? = null,
    @Version val version: Short = 0,
    @ManyToOne(optional = false, fetch = FetchType.LAZY) val beOnTheLookOut: BeOnTheLookOut,
    @Column(columnDefinition = "geometry(Point, 4326)", nullable = false)
    val location: Geometry,
    @CreationTimestamp
    val date: Date? = null,
)