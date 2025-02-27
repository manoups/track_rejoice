package one.breece.track_rejoice.commands

import java.time.LocalDateTime

data class APBResponse(val id: Long,
                       val enabled: Boolean,
                       val name: String,
                       val breed: String,
                       val color: String? = null,
                       val phoneNumber: String? = null,
                       val address: AddressCommand?,
                       val lastSeenDate: LocalDateTime,
                       val additionalInformation: String? = null)
