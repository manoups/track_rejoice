package one.breece.track_rejoice.service.impl

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import one.breece.track_rejoice.commands.ItemAnnouncementCommand
import one.breece.track_rejoice.commands.ItemResponseCommand
import one.breece.track_rejoice.commands.LatLng
import one.breece.track_rejoice.domain.Item
import one.breece.track_rejoice.repository.ItemRepository
import one.breece.track_rejoice.service.ItemService
import org.locationtech.jts.geom.GeometryFactory
import org.locationtech.jts.geom.MultiPoint
import org.locationtech.jts.geom.Point
import org.locationtech.jts.geom.impl.PackedCoordinateSequenceFactory
import org.springframework.stereotype.Service


@Service
class ItemServiceImpl(private val repository: ItemRepository, private val objectMapper: ObjectMapper) : ItemService {
    override fun createAPB(announcementCommand: ItemAnnouncementCommand): ItemResponseCommand {
        val multipoint: List<LatLng> =
            objectMapper.readValue(announcementCommand.latlngs!!, object : TypeReference<List<LatLng>>() {})
        val geofence = Item(announcementCommand.shortDescription!!, announcementCommand.color,makeMultiPoint(multipoint))
            .also {
                it.phoneNumber = announcementCommand.phoneNumber; it.extraInfo =
                announcementCommand.additionalInformation
            }
            .let { repository.save(it) }
        return ItemResponseCommand(geofence.id!!, announcementCommand.shortDescription, announcementCommand.color, announcementCommand.phoneNumber, announcementCommand.lastSeenDate!!, announcementCommand.additionalInformation,
            multipoint.map { doubleArrayOf(it.lng, it.lat) })
    }

    private fun makeMultiPoint(latLngList: List<LatLng>): MultiPoint {
        val latLng = latLngList.map { makePoint(it.lng, it.lat) }.toTypedArray()
        val gf = GeometryFactory()
        return gf.createMultiPoint(latLng)
    }

    private fun makePoint(lon: Double, lat: Double): Point {
        val gf = GeometryFactory()
        val sf = PackedCoordinateSequenceFactory()
        return gf.createPoint(sf.create(doubleArrayOf(lon, lat), 2))
    }

    override fun deleteById(id: Long) {
        repository.deleteById(id)
    }

    override fun readById(id: Long): ItemResponseCommand? {
        repository.findById(id).let { optional ->
            return if (optional.isPresent) {
                val item = optional.get()
                 ItemResponseCommand(item.id!!, item.shortDescription, item.phoneNumber, item.color, item.lastSeenDate, item.extraInfo,
                    item.lastSeenLocation.coordinates.map { doubleArrayOf(it.y, it.x) })
            } else {
                null
            }
        }
    }

}