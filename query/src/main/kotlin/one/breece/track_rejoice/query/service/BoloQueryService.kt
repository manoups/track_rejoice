package one.breece.track_rejoice.query.service

import one.breece.track_rejoice.core.projections.BeOnTheLookOutProj
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface BoloQueryService {
    fun findAllByLngLat(lng: Double, lat: Double, distanceInMeters: Double, pageable: Pageable): Page<BeOnTheLookOutProj>
}
