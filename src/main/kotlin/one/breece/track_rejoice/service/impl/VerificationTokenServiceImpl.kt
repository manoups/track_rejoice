package one.breece.track_rejoice.service.impl

import jakarta.servlet.http.HttpServletRequest
import one.breece.track_rejoice.domain.command.AppUser
import one.breece.track_rejoice.domain.command.VerificationToken
import one.breece.track_rejoice.repository.command.VerificationTokenRepository
import one.breece.track_rejoice.service.VerificationTokenService
import org.springframework.context.MessageSource
import org.springframework.core.env.Environment
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service
import java.util.*

@Service
class VerificationTokenServiceImpl(
    private var messages: MessageSource,
    private val env: Environment,
    private val mailSender: JavaMailSender,
    val tokenRepository: VerificationTokenRepository
) : VerificationTokenService {
    override fun resendRegistrationTokenForm(existingToken: String, request: HttpServletRequest) {
        val newToken = generateNewVerificationToken(existingToken)
        val user = getUser(newToken.token!!)
        val appUrl = "http://${request.serverName}:${request.serverPort}${request.contextPath}"
        val email: SimpleMailMessage = constructResetVerificationTokenEmail(appUrl, request.locale, newToken, user)
        mailSender.send(email)
    }

    private fun constructResetVerificationTokenEmail(
        contextPath: String,
        locale: Locale,
        newToken: VerificationToken,
        user: AppUser
    ): SimpleMailMessage {
        val confirmationUrl = "${contextPath}/register/confirm?token=${newToken.token}"
        val message = messages.getMessage("message.resendToken", null, locale)
        val email = SimpleMailMessage()
        email.subject = "Resend Registration Token"
        email.text = "$message \r\n$confirmationUrl"
        email.setTo(user.username)
        email.from = env.getProperty("support.email")
        return email
    }

    private fun generateNewVerificationToken(existingVerificationToken: String): VerificationToken {
        var vToken = tokenRepository.findByToken(existingVerificationToken).orElseThrow { RuntimeException("Token not found") }
        vToken.updateToken(
            UUID.randomUUID()
                .toString()
        )
        vToken = tokenRepository.save(vToken)
        return vToken
    }

    private fun getUser(verificationToken: String): AppUser {
        return tokenRepository.findByToken(verificationToken).map { it.user }.get()
    }
}