package one.breece.track_rejoice.web.dto

import org.locationtech.jts.geom.Geometry
import java.util.*

data class TraceDTO(val point: Geometry, val date: Date)

