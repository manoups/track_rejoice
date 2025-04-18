package one.breece.track_rejoice.security.captcha

import one.breece.track_rejoice.security.config.CaptchaSettings
import one.breece.track_rejoice.security.error.ReCaptchaInvalidException
import one.breece.track_rejoice.security.service.LoginAttemptService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.util.StringUtils
import org.springframework.web.client.RestOperations
import java.util.regex.Pattern

abstract class AbstractCaptchaService(
    protected var captchaSettings: CaptchaSettings,
    protected var reCaptchaAttemptService: ReCaptchaAttemptService,
    protected val loginAttemptService: LoginAttemptService,
    protected var restTemplate: RestOperations
) : ICaptchaService {

    override val reCaptchaSite: String
        get() = captchaSettings.site

    override val reCaptchaSecret: String
        get() = captchaSettings.secret

    protected fun securityCheck(response: String) {
        LOGGER.debug("Attempting to validate response {}", response)

        if (reCaptchaAttemptService.isBlocked(loginAttemptService.clientIP())) {
            throw ReCaptchaInvalidException("Client exceeded maximum number of failed attempts")
        }

        if (!responseSanityCheck(response)) {
            throw ReCaptchaInvalidException("Response contains invalid characters")
        }
    }

    protected fun responseSanityCheck(response: String): Boolean {
        return StringUtils.hasLength(response) && RESPONSE_PATTERN.matcher(response).matches()
    }

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(AbstractCaptchaService::class.java)

        protected val RESPONSE_PATTERN: Pattern = Pattern.compile("[A-Za-z0-9_-]+")

        const val RECAPTCHA_URL_TEMPLATE: String =
            "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s&remoteip=%s"
    }
}
