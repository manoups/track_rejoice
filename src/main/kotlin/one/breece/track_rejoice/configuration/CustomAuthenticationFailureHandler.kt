package one.breece.track_rejoice.configuration

import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import one.breece.track_rejoice.service.LoginAttemptService
import org.springframework.context.MessageSource
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.WebAttributes
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler
import org.springframework.stereotype.Component
import org.springframework.web.servlet.LocaleResolver
import java.io.IOException

@Component("authenticationFailureHandler")
class CustomAuthenticationFailureHandler(
    private val loginAttemptService: LoginAttemptService,
    private val localeResolver: LocaleResolver,
    private val messages: MessageSource
) : SimpleUrlAuthenticationFailureHandler() {

    @Throws(IOException::class, ServletException::class)
    override fun onAuthenticationFailure(
        request: HttpServletRequest,
        response: HttpServletResponse,
        exception: AuthenticationException
    ) {
        setDefaultFailureUrl("/login?error=true")

        super.onAuthenticationFailure(request, response, exception)

        val locale = localeResolver.resolveLocale(request)

        var errorMessage = messages.getMessage("message.badCredentials", null, locale)

        if (loginAttemptService.isBlocked) {
            errorMessage = messages.getMessage("auth.message.blocked", null, locale)
        }

        if (exception.message
                .equals("User is disabled", ignoreCase = true)
        ) {
            errorMessage = messages.getMessage("auth.message.disabled", null, locale)
        } else if (exception.message
                .equals("User account has expired", ignoreCase = true)
        ) {
            errorMessage = messages.getMessage("auth.message.expired", null, locale)
        } else if (exception.message
                .equals("blocked", ignoreCase = true)
        ) {
            errorMessage = messages.getMessage("auth.message.blocked", null, locale)
        } else if (exception.message
                .equals("unusual location", ignoreCase = true)
        ) {
            errorMessage = messages.getMessage("auth.message.unusual.location", null, locale)
        }

        request.session
            .setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, errorMessage)
    }
}