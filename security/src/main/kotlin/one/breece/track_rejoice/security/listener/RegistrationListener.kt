package one.breece.track_rejoice.security.listener

import one.breece.track_rejoice.security.domain.AppUserDetails
import one.breece.track_rejoice.security.event.OnRegistrationCompleteEvent
import one.breece.track_rejoice.security.service.MailService
import one.breece.track_rejoice.security.service.UserService
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class RegistrationListener(
    private val service: UserService,
    private val validationTokenMailServiceImpl: MailService
) : ApplicationListener<OnRegistrationCompleteEvent> {

    // API
    override fun onApplicationEvent(event: OnRegistrationCompleteEvent) {
        this.confirmRegistration(event)
    }

    private fun confirmRegistration(event: OnRegistrationCompleteEvent) {
        val user: AppUserDetails = event.user
        val token = UUID.randomUUID().toString()
        service.createVerificationTokenForUser(user, token)
        validationTokenMailServiceImpl.send(event.request, token, user)
    }
}