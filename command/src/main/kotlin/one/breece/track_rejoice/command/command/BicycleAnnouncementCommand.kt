package one.breece.track_rejoice.command.command

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

data class BicycleAnnouncementCommand(
    @field:NotBlank val color: String? = null,
    @field:NotBlank val maker: String? = null,
    @field:NotBlank val model: String? = null,
    @field:NotNull val year: Short? = null,
    @field:NotNull @field:Pattern(regexp = "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$")
    val phoneNumber: String? = null,
    @field:NotNull @field:DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    val lastSeenDate: LocalDateTime? = null,
    @Size(max = 500) val additionalInformation: String? = null,
    @field:NotNull val lat: Double? = null,
    @field:NotNull val lon: Double? = null,
    @field:NotNull val initializedCoords: Boolean? = null
)