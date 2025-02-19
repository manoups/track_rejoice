package one.breece.track_rejoice.commands

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.time.LocalDateTime

class PetSearchCommand(
    @field:NotBlank @field:Size(min = 2, max = 50) val name: String? = null,
    @field:NotBlank @field:Size(min = 10, max = 50) val breed: String? = null,
    val color: String? = null,
    @field:NotNull val lastSeenDate: LocalDateTime? = null,
)