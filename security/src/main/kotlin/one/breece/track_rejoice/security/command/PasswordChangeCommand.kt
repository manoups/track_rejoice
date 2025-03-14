package one.breece.track_rejoice.security.command

import jakarta.validation.constraints.NotBlank
import one.breece.track_rejoice.security.validation.ValidPassword
import org.apache.commons.lang3.StringUtils

class PasswordChangeCommand(
    var oldPassword: String? = null,
    @field: NotBlank
    @field: ValidPassword
    var newPassword: String? = null
) {
    @NotBlank
    var token: String? = null
        set(value) {
            field = StringUtils.trimToNull(value)
        }
}
