package one.breece.track_rejoice.commands

import one.breece.track_rejoice.web.dto.PhotoDescriptor
import java.time.LocalDateTime
import java.util.UUID

class ItemResponseCommand(
    val id: Long? = null,
    val enabled: Boolean,
    val shortDescription: String? = null, //keys/glove/airpod/etc
    val phoneNumber: String? = null,
    val color: String? = null,
    val lastSeenDate: LocalDateTime? = null,
    val additionalInformation: String? = null,
    val latlngs: List<DoubleArray>? = null,
    val sku: UUID,
    val photos: List<PhotoDescriptor> = emptyList()
)
