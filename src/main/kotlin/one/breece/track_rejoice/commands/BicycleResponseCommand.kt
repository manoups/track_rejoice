package one.breece.track_rejoice.commands

import one.breece.track_rejoice.web.dto.PhotoDescriptor
import java.time.LocalDateTime
import java.util.*

data class BicycleResponseCommand(
    val id: Long,
    val enabled: Boolean,
    val color: String,
    val maker: String,
    val model: String,
    val year: Short?,
    val phoneNumber: String,
    val lastSeenDate: LocalDateTime,
    val additionalInformation: String?,
    val lat: Double? = null,
    val lon: Double? = null,
    val sku: UUID,
    val photos: List<PhotoDescriptor> = emptyList()
)
