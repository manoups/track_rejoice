package one.breece.track_rejoice.commands

import java.time.LocalDateTime

data class APBResponse(
    val id: Long,
    val species: String,
    val enabled: Boolean,
    val name: String,
    val breed: String,
    val color: String? = null,
    val phoneNumber: String? = null,
    var address: AddressCommand? = null,
    var lastSeenDate: LocalDateTime? = null,
    val additionalInformation: String? = null,
    val sex: String? = null,
    val lat: Double? = null,
    val lon: Double? = null
)
