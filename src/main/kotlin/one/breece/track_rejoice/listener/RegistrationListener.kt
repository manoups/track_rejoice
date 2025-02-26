package one.breece.track_rejoice.listener

import one.breece.track_rejoice.web.dto.AppUserDetails
import one.breece.track_rejoice.event.OnRegistrationCompleteEvent
import one.breece.track_rejoice.service.UserService
import org.springframework.context.ApplicationListener
import org.springframework.context.MessageSource
import org.springframework.core.env.Environment
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Component
import java.util.*

@Component
class RegistrationListener(
    private val service: UserService,
    private val messages: MessageSource,
    private val mailSender: JavaMailSender,
    private val env: Environment,
) : ApplicationListener<OnRegistrationCompleteEvent> {


    // API
    override fun onApplicationEvent(event: OnRegistrationCompleteEvent) {
        this.confirmRegistration(event)
    }

    private fun confirmRegistration(event: OnRegistrationCompleteEvent) {
        val user: AppUserDetails = event.user
        val token = UUID.randomUUID().toString()
        service.createVerificationTokenForUser(user, token)

        val email: SimpleMailMessage = constructEmailMessage(event, user, token)
        mailSender.send(email)
    }

    //
    private fun constructEmailMessage(
        event: OnRegistrationCompleteEvent,
        user: AppUserDetails,
        token: String
    ): SimpleMailMessage {
        val recipientAddress: String = user.username
        val subject = "Registration Confirmation"
        val confirmationUrl = "${event.appUrl}/register/confirm?token=${token}"
        val message = messages.getMessage(
            "message.regSuccLink",
            null,
            "You registered successfully. To confirm your registration, please click on the below link.",
            event.locale
        )
        val email = SimpleMailMessage()
        email.setTo(recipientAddress)
        email.subject = subject
        email.text = "$message \r\n$confirmationUrl"
        email.from = env.getProperty("support.email")
        return email
    }
}