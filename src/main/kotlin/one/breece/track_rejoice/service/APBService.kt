package one.breece.track_rejoice.service

import one.breece.track_rejoice.commands.PetAnnouncementCommand
import one.breece.track_rejoice.commands.APBResponse

interface APBService {
    fun createAPB(petAnnouncementCommand: PetAnnouncementCommand): APBResponse
    fun deleteById(id: Long)
    fun enableAnnouncement(announcementId: Long)
}
