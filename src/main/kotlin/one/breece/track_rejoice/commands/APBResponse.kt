package one.breece.track_rejoice.commands

import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

data class APBResponse(val id: Long,
                       val enabled: Boolean,
                       val name: String,
                       val breed: String,
                       val color: String? = null,
                       val phoneNumber: String? = null,
                       var address: AddressCommand? = null,
                       var lastSeenDate: LocalDateTime? = null,
                       val additionalInformation: String? = null)
