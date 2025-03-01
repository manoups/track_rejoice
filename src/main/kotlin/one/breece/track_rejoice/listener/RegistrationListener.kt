package one.breece.track_rejoice.listener

import one.breece.track_rejoice.event.OnRegistrationCompleteEvent
import one.breece.track_rejoice.service.MailService
import one.breece.track_rejoice.service.UserService
import one.breece.track_rejoice.web.dto.AppUserDetails
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