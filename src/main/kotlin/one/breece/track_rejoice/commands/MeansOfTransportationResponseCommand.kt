package one.breece.track_rejoice.commands

import java.time.LocalDateTime

data class MeansOfTransportationResponseCommand(
    val id: Long,
    val color: String,
    val maker: String,
    val model: String,
    val plate: String?,
    val year: Short?,
    val phoneNumber: String,
    val lastSeenDate: LocalDateTime,
    val additionalInformation: String?,
    val lat: Double? = null,
    val lon: Double? = null
)
