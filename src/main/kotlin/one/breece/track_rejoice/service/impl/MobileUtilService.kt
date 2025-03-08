package one.breece.track_rejoice.service.impl

import org.locationtech.jts.geom.GeometryFactory
import org.locationtech.jts.geom.Point
import org.locationtech.jts.geom.impl.PackedCoordinateSequenceFactory
import org.springframework.stereotype.Service

@Service
class MobileUtilService {
    fun makePoint(lon: Double, lat: Double) : Point {
        val gf = GeometryFactory()
        val sf = PackedCoordinateSequenceFactory()
        return gf.createPoint(sf.create(doubleArrayOf(lon, lat), 2))
    }
}
