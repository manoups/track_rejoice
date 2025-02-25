package one.breece.track_rejoice.commands

import java.time.LocalDateTime

data class APBResponse(val id: Long,
                       val name: String,
                       val breed: String,
                       val color: String? = null,
                       val address: AddressCommand = AddressCommand(),
                       val lastSeenDate: LocalDateTime,
                       val additionalInformation: String? = null)
