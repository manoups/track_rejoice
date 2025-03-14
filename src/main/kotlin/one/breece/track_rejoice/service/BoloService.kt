package one.breece.track_rejoice.service

import one.breece.track_rejoice.core.projections.BeOnTheLookOutProj
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.*

interface BoloService {
    fun enableAnnouncement(announcementId: Long)
    fun findAll(pageable: Pageable): Page<BeOnTheLookOutProj>
    fun deleteBySku(sku: UUID)
}
