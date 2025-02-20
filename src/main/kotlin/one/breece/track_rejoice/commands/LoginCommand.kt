package one.breece.track_rejoice.commands

import jakarta.validation.constraints.NotBlank

data class LoginCommand(
    @field:NotBlank val username: String? = null,
    @field:NotBlank val password: String? = null
)