package one.breece.track_rejoice.service.impl

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import one.breece.track_rejoice.commands.ItemAnnouncementCommand
import one.breece.track_rejoice.commands.ItemResponseCommand
import one.breece.track_rejoice.core.util.LatLng
import one.breece.track_rejoice.core.util.GeometryUtil
import one.breece.track_rejoice.domain.command.Item
import one.breece.track_rejoice.repository.command.ItemRepository
import one.breece.track_rejoice.service.ItemService
import one.breece.track_rejoice.web.dto.PhotoDescriptor
import org.apache.commons.io.FilenameUtils
import org.springframework.stereotype.Service
import java.lang.RuntimeException
import java.util.*


@Service
class ItemServiceImpl(private val repository: ItemRepository, private val objectMapper: ObjectMapper) : ItemService {
    override fun createAPB(announcementCommand: ItemAnnouncementCommand): ItemResponseCommand {
        val multipoint: List<LatLng> =
            objectMapper.readValue(announcementCommand.latlngs!!, object : TypeReference<List<LatLng>>() {})
        val geofence =
            Item(
                announcementCommand.shortDescription!!,
                announcementCommand.color,
                announcementCommand.phoneNumber!!,
                GeometryUtil.makeMultiPoint(multipoint)
            )
                .also {
                    it.lastSeenDate = announcementCommand.lastSeenDate!!
                    it.extraInfo = announcementCommand.additionalInformation
                }
                .let { repository.save(it) }
        return ItemResponseCommand(
            geofence.id!!,
            geofence.enabled,
            announcementCommand.shortDescription,
            announcementCommand.color,
            announcementCommand.phoneNumber,
            announcementCommand.lastSeenDate!!,
            announcementCommand.additionalInformation,
            multipoint.map { doubleArrayOf(it.lng, it.lat) },
            geofence.sku
        )
    }

    override fun deleteById(id: Long) {
        repository.deleteById(id)
    }

    override fun readById(id: Long): ItemResponseCommand? {
        repository.findById(id).let { optional ->
            return if (optional.isPresent) {
                val item = optional.get()
                ItemResponseCommand(
                    item.id!!, item.enabled, item.shortDescription, item.phoneNumber, item.color, item.lastSeenDate, item.extraInfo,
                    item.lastSeenLocation.coordinates.map { doubleArrayOf(it.y, it.x) }, item.sku, item.photo.map { PhotoDescriptor("https://${it.bucket}.s3.amazonaws.com/${it.key}", it.key) })
            } else {
                null
            }
        }
    }

    override fun readBySku(sku: UUID): ItemResponseCommand {
        repository.findBySku(sku).let { optional ->
            return if (optional.isPresent) {
                val item = optional.get()
                ItemResponseCommand(
                    item.id!!, item.enabled, item.shortDescription, item.phoneNumber, item.color, item.lastSeenDate, item.extraInfo,
                    item.lastSeenLocation.coordinates.map { doubleArrayOf(it.y, it.x) }, item.sku,  item.photo.map { PhotoDescriptor("https://${it.bucket}.s3.amazonaws.com/${it.key}", FilenameUtils.getName(it.key)) })
            } else {
                throw RuntimeException("Item with sku=$sku not found")
            }
        }
    }
}