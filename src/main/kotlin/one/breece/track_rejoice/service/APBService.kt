package one.breece.track_rejoice.service

import one.breece.track_rejoice.commands.APBResponse

interface APBService<in APBCommand> {
    fun createAPB(petAnnouncementCommand: APBCommand): APBResponse
    fun deleteById(id: Long)
    fun enableAnnouncement(announcementId: Long)
    fun readById(id: Long): APBResponse?
}
