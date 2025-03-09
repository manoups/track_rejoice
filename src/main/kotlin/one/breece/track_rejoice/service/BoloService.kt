package one.breece.track_rejoice.service

import one.breece.track_rejoice.domain.BeOnTheLookOut
import one.breece.track_rejoice.repository.projections.BeOnTheLookOutProj
import one.breece.track_rejoice.web.dto.BoloResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.*

interface BoloService {
    fun enableAnnouncement(announcementId: Long)
    fun findAllByLngLat(lon: Double, lat: Double, distanceInMeters: Double, pageable: Pageable): Page<BeOnTheLookOutProj>
    fun findAll(pageable: Pageable): Page<BeOnTheLookOut>
    fun deleteBySku(sku: UUID)
}
