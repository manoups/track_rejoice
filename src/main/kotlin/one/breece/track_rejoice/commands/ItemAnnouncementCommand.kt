package one.breece.track_rejoice.commands

import jakarta.validation.constraints.*
import java.time.LocalDateTime

data class ItemAnnouncementCommand(
    @field:NotBlank val shortDescription: String? = null, //keys/glove/airpod/etc
    @field:NotNull @field:Pattern(regexp = "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$")
    val phoneNumber: String? = null,
    val color: String?=null,
    val lastSeenDate: LocalDateTime? = null,
    @Size(max = 500) val additionalInformation: String? = null,
    @field:NotBlank val latlngs: String? = null
)
