package one.breece.track_rejoice.service

import one.breece.track_rejoice.repository.projections.BeOnTheLookOutProj
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface BoloService {
    fun enableAnnouncement(announcementId: Long)
    fun findAllByLngLat(lon: Double, lat: Double, distanceInMeters: Double, pageable: Pageable): Page<BeOnTheLookOutProj>
}
