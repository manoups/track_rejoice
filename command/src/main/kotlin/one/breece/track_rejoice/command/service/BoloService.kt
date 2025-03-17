package one.breece.track_rejoice.command.service

import one.breece.track_rejoice.core.projections.BeOnTheLookOutProj
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.*

interface BoloService {
    fun activateAnnouncement(announcementId: Long)
    fun findAll(pageable: Pageable): Page<BeOnTheLookOutProj>
    fun deleteBySku(sku: UUID)
}
