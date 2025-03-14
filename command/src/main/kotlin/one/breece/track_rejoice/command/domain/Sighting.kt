package one.breece.track_rejoice.command.domain

import jakarta.persistence.Entity
import one.breece.track_rejoice.core.domain.BoilerPlate
import org.locationtech.jts.geom.Point
import java.time.LocalDateTime

@Entity
class Sighting(val point: Point, val moment: LocalDateTime): BoilerPlate()