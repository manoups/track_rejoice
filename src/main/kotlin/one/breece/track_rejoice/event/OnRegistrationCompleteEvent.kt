package one.breece.track_rejoice.event

import jakarta.servlet.http.HttpServletRequest
import one.breece.track_rejoice.web.dto.AppUserDetails
import org.springframework.context.ApplicationEvent

data class OnRegistrationCompleteEvent(
    val user: AppUserDetails,
    val request: HttpServletRequest
) : ApplicationEvent(user)