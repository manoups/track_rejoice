package one.breece.track_rejoice.security.controller

import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import one.breece.track_rejoice.security.command.PasswordChangeCommand
import one.breece.track_rejoice.security.service.MailService
import one.breece.track_rejoice.security.service.PasswordResetTokenService
import one.breece.track_rejoice.security.service.UserService
import one.breece.track_rejoice.security.util.GenericResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class RegistrationRestController(
    private val userService: UserService,
    private val passwordResetTokenService: PasswordResetTokenService,
    private val resetTokenMailServiceImpl: MailService
) {
    @PostMapping("/password-reset")
    fun resetPassword(request: HttpServletRequest, @RequestParam("email") userEmail: String): GenericResponse {
        val user = userService.loadUserByUsername(userEmail)
        val token =
            passwordResetTokenService.createPasswordResetTokenForUser(user.username)
        resetTokenMailServiceImpl.send(request, token.token, user)
        return GenericResponse("message.resetPasswordEmail")
    }

    @PostMapping("/password-save")
    fun savePassword(locale: Locale, @Valid passwordChangeCommand: PasswordChangeCommand): GenericResponse {
        return passwordResetTokenService.savePassword(passwordChangeCommand,  locale)

    }

}