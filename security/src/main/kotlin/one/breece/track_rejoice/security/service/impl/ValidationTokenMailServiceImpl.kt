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
class ValidationTokenMailServiceImpl(
    private val messages: MessageSource,
    private val mailSender: JavaMailSender,
    val service: MailServiceUtils
) : MailService {
    override fun send(request: HttpServletRequest, token: String, user: UserDetails) {
        mailSender.send(constructEmailMessage(service.getAppUrl(request), request.locale, token, user))
    }

    private fun constructEmailMessage(
        appUrl: String,
        locale: Locale,
        token: String,
        user: UserDetails
    ): SimpleMailMessage {
        val subject = "Registration Confirmation"
        val confirmationUrl = "${appUrl}/register/confirm?token=${token}"
        val message = messages.getMessage(
            "message.regSuccLink",
            null,
            "You registered successfully. To confirm your registration, please click on the below link.",
            locale
        )
        return service.constructEmail(subject, "$message \r\n$confirmationUrl", user.username)
    }
}
