package one.breece.track_rejoice.security.service.impl

import jakarta.servlet.http.HttpServletRequest
import org.springframework.core.env.Environment
import org.springframework.mail.SimpleMailMessage
import org.springframework.stereotype.Service

@Service
class MailServiceUtils(private val env: Environment) {
    fun constructEmail(subject: String, body: String, emailAddress: String): SimpleMailMessage {
        val email = SimpleMailMessage()
        email.subject = subject
        email.text = body
        email.setTo(emailAddress)
        email.from = env.getProperty("support.email")
        return email
    }

    fun getAppUrl(request: HttpServletRequest): String {
        return "http://" + request.serverName + ":" + request.serverPort + request.contextPath
    }
}