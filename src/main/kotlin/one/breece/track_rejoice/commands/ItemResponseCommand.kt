package one.breece.track_rejoice.commands

import java.time.LocalDateTime

class ItemResponseCommand(
    val id: Long? = null,
    val shortDescription: String? = null, //keys/glove/airpod/etc
    val phoneNumber: String? = null,
    val color: String? = null,
    val lastSeenDate: LocalDateTime? = null,
    val additionalInformation: String? = null,
    val latlngs: List<DoubleArray>? = null
)
