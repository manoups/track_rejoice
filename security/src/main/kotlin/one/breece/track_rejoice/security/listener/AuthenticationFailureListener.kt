package one.breece.track_rejoice.security.listener

import jakarta.servlet.http.HttpServletRequest
import one.breece.track_rejoice.security.service.LoginAttemptService
import org.springframework.context.ApplicationListener
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent
import org.springframework.stereotype.Component


@Component
class AuthenticationFailureListener(
    private val request: HttpServletRequest,
    private val loginAttemptService: LoginAttemptService
) : ApplicationListener<AuthenticationFailureBadCredentialsEvent> {
    override fun onApplicationEvent(e: AuthenticationFailureBadCredentialsEvent) {
        val xfHeader = request.getHeader("X-Forwarded-For")
        if (xfHeader == null || xfHeader.isEmpty() || !xfHeader.contains(request.remoteAddr)) {
            loginAttemptService.loginFailed(request.remoteAddr)
        } else {
            loginAttemptService.loginFailed(xfHeader.split(",".toRegex()).dropLastWhile { it.isEmpty() }
                .toTypedArray()[0])
        }
    }
}