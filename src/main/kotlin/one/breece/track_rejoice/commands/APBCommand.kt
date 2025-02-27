package one.breece.track_rejoice.commands

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

data class APBCommand(
    @field:NotBlank @field:Size(min = 2, max = 50) val name: String? = null,
    @field:NotBlank @field:Size(min = 3, max = 50) val breed: String? = null,
    val color: String? = null,
    val address: AddressCommand = AddressCommand(),
    @field:NotNull @field:Pattern(regexp = "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$")
    val phoneNumber: String? = null,
    @field:NotNull @field:DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    val lastSeenDate: LocalDateTime? = null,
    @Size(max = 500) val additionalInformation: String? = null,
)