package one.breece.track_rejoice.service.impl

import one.breece.track_rejoice.repository.BeOnTheLookOutRepository
import one.breece.track_rejoice.service.BoloService
import org.springframework.stereotype.Service

@Service
class BoloServiceImpl(private val repository: BeOnTheLookOutRepository):BoloService {
    override fun enableAnnouncement(announcementId: Long) {
        repository.findById(announcementId).ifPresent{
            it.enabled = true
            repository.save(it)
        }
    }
}