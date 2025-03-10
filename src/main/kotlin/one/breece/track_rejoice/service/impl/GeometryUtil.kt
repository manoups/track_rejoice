package one.breece.track_rejoice.service.impl

import org.locationtech.jts.geom.GeometryFactory
import org.locationtech.jts.geom.Point
import org.locationtech.jts.geom.PrecisionModel
import org.locationtech.jts.geom.impl.PackedCoordinateSequenceFactory

class GeometryUtil {
    companion object {
        private const val SRID: Int = 4326

        fun parseLocation(x: Double, y: Double): Point {
            val gf = GeometryFactory(PrecisionModel(), SRID)
            val sf = PackedCoordinateSequenceFactory()
            return gf.createPoint(sf.create(doubleArrayOf(x, y), 2))
        }
    }
}