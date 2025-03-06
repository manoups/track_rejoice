package one.breece.track_rejoice.commands

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import java.time.LocalDateTime

data class ItemAnnouncementCommand(
   val name: String? = null, //keys/glove/airpod/etc
    @field:NotBlank val ownerName: String? = null,
    @field:NotNull @field:Pattern(regexp = "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$")
    val phoneNumber: String? = null,
   val color: String?=null,
    val lastSeenDate: LocalDateTime? = null,
    @Size(max = 500) val additionalInformation: String? = null,
    @field:NotNull val lat: Double? = null,
    @field:NotNull val lon: Double? = null
) : APBCommand()
