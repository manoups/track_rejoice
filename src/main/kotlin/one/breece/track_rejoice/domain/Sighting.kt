package one.breece.track_rejoice.domain

import jakarta.persistence.Entity
import org.locationtech.jts.geom.Point
import java.time.LocalDateTime

@Entity
class Sighting(val point: Point, val moment: LocalDateTime):BoilerPlate()