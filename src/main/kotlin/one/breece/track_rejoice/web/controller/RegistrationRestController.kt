package one.breece.track_rejoice.web.controller

import jakarta.servlet.http.HttpServletRequest
import one.breece.track_rejoice.service.MailService
import one.breece.track_rejoice.service.PasswordResetTokenService
import one.breece.track_rejoice.service.UserService
import one.breece.track_rejoice.util.GenericResponse
import org.springframework.context.MessageSource
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class RegistrationRestController(
    private val messages: MessageSource,
    private val userService: UserService,
    private val passwordResetTokenService: PasswordResetTokenService,
    private val resetTokenMailServiceImpl: MailService
) {
    @PostMapping("/resetPassword")
    fun resetPassword(request: HttpServletRequest, @RequestParam("email") userEmail: String): GenericResponse {
        val user = userService.loadUserByUsername(userEmail)
        val token =
            passwordResetTokenService.createPasswordResetTokenForUser(user.username)
        resetTokenMailServiceImpl.send(request, token.token, user)
        return GenericResponse(messages.getMessage("message.resetPasswordEmail", null, request.locale))
    }


}