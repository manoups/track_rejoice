package one.breece.track_rejoice.service.impl

import one.breece.track_rejoice.commands.APBResponse
import one.breece.track_rejoice.commands.ItemAnnouncementCommand
import one.breece.track_rejoice.repository.ItemRepository
import one.breece.track_rejoice.service.ItemService
import org.springframework.stereotype.Service

@Service
class ItemServiceImpl(private val repository: ItemRepository) : ItemService {
    override fun createAPB(petAnnouncementCommand: ItemAnnouncementCommand): APBResponse {
        TODO("Not yet implemented")
    }

    override fun deleteById(id: Long) {
        repository.deleteById(id)
    }

    override fun enableAnnouncement(announcementId: Long) {
        TODO("Not yet implemented")
    }

    override fun readById(id: Long): APBResponse? {
        TODO("Not yet implemented")
    }

}