package one.breece.track_rejoice.security.command

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class UserCommand(
    @field:NotBlank @field:Size(min = 2, max = 50)
    val firstName: String? = null,
    @field:NotBlank @field:Size(min = 2, max = 50)
    val lastName: String? = null,
    @field:NotBlank @field:Size(min = 8, max = 20)
    val password: String? = null,
    @field:NotBlank
    @field:Email
    val email: String? = null
)
