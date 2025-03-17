package one.breece.track_rejoice.command.service.impl

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.transaction.Transactional
import one.breece.track_rejoice.command.command.ItemAnnouncementCommand
import one.breece.track_rejoice.command.domain.Item
import one.breece.track_rejoice.command.events.CreateQR
import one.breece.track_rejoice.command.repository.ItemRepository
import one.breece.track_rejoice.command.service.ItemService
import one.breece.track_rejoice.core.command.ItemResponseCommand
import one.breece.track_rejoice.core.util.GeometryUtil
import one.breece.track_rejoice.core.util.LatLng
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.ApplicationEventPublisher
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Service
import java.util.*


@Service
class ItemServiceImpl(
    private val repository: ItemRepository,
    private val objectMapper: ObjectMapper,
    private val itemToItemResponseCommand: Converter<Item, ItemResponseCommand>,
    val applicationEventPublisher: ApplicationEventPublisher
) : ItemService {
    @Value("\${aws.s3.bucket}")
    lateinit var bucketName: String

    @Transactional
    override fun createBolo(announcementCommand: ItemAnnouncementCommand): ItemResponseCommand {
        val multipoint: List<LatLng> =
            objectMapper.readValue(announcementCommand.latlngs!!, object : TypeReference<List<LatLng>>() {})
        val item =
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
        applicationEventPublisher.publishEvent(CreateQR("http://localhost:8081/details/item/${item.sku}", bucketName, "qr-code/${item.sku}.png", item.id!!))
        return itemToItemResponseCommand.convert(item)!!
    }

    override fun deleteById(id: Long) {
        repository.deleteById(id)
    }

    override fun readBySku(sku: UUID): ItemResponseCommand {
        repository.findBySku(sku).let { optional ->
            return if (optional.isPresent) {
                val item = optional.get()
                itemToItemResponseCommand.convert(item)!!
            } else {
                throw RuntimeException("Item with sku=$sku not found")
            }
        }
    }
}