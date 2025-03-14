package one.breece.track_rejoice.security.service.impl

import jakarta.servlet.http.HttpServletRequest
import one.breece.track_rejoice.security.service.MailService
import org.springframework.context.MessageSource
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.*

@Service
class ResetTokenMailServiceImpl(
    private val messages: MessageSource,
    private val mailSender: JavaMailSender,
    val service: MailServiceUtils
) : MailService {
    override fun send(request: HttpServletRequest, token: String, user: UserDetails) {
        mailSender.send(constructResetTokenEmail(service.getAppUrl(request), request.locale, token, user))
    }

    private fun constructResetTokenEmail(
        contextPath: String,
        locale: Locale,
        token: String,
        user: UserDetails
    ): SimpleMailMessage {
        val url = "$contextPath/password-change?token=$token"
        val message = messages.getMessage("message.resetPassword", null, locale)
        return service.constructEmail("Reset Password", "$message \r\n$url", user.username)
    }
}