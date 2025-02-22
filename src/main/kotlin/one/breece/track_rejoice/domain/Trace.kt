package one.breece.track_rejoice.domain

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.locationtech.jts.geom.Point
import java.util.*

@Entity
class Trace(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long? = null,
    @Version val version: Short = 0,
    @ManyToOne(optional = false, fetch = FetchType.LAZY) val allPointsBulletin: AllPointsBulletin? = null,
    @Column(columnDefinition = "geometry(Point, 4326)", nullable = false)
    val location: Point,
    @CreationTimestamp
    val date: Date? = null,
)