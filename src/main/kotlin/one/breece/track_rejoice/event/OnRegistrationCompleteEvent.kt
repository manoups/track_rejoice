package one.breece.track_rejoice.event

import one.breece.track_rejoice.web.dto.AppUserDetails
import org.springframework.context.ApplicationEvent
import java.util.*

data class OnRegistrationCompleteEvent(
    val user: AppUserDetails,
    val locale: Locale,
    val appUrl: String
) : ApplicationEvent(user)