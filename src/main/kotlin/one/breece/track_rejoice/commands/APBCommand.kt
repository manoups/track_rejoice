package one.breece.track_rejoice.commands

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.time.LocalDateTime

data class APBCommand(
    @field:NotBlank @field:Size(min = 2, max = 50) val name: String? = null,
    @field:NotBlank @field:Size(min = 3, max = 50) val breed: String? = null,
    val color: String? = null,
    val address: AddressCommand = AddressCommand(),
    @field:NotNull val lastSeenDate: LocalDateTime? = null,
)