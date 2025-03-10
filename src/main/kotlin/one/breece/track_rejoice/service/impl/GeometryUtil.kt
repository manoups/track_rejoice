package one.breece.track_rejoice.service.impl

import one.breece.track_rejoice.commands.LatLng
import org.locationtech.jts.geom.GeometryFactory
import org.locationtech.jts.geom.MultiPoint
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

        fun makeMultiPoint(latLngList: List<LatLng>): MultiPoint {
            val latLng = latLngList.map { parseLocation(it.lng, it.lat) }.toTypedArray()
            val gf = GeometryFactory(PrecisionModel(), SRID)
            return gf.createMultiPoint(latLng)
        }
    }
}