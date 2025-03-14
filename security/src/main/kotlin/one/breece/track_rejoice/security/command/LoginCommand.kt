package one.breece.track_rejoice.security.command

import jakarta.validation.constraints.NotBlank
import org.apache.commons.lang3.StringUtils

class LoginCommand(
    @field:NotBlank
    val password: String? = null
) {
    @field:NotBlank
    var username: String? = null
        set(value) {
            field = StringUtils.trimToNull(StringUtils.lowerCase(value))
        }
}