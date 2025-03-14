package one.breece.track_rejoice.security.captcha

import one.breece.track_rejoice.security.config.CaptchaSettings
import one.breece.track_rejoice.security.dto.GoogleResponse
import one.breece.track_rejoice.security.error.ReCaptchaInvalidException
import one.breece.track_rejoice.security.error.ReCaptchaUnavailableException
import one.breece.track_rejoice.security.service.LoginAttemptService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestOperations
import java.net.URI

@Service("captchaServiceV3")
class CaptchaServiceV3(captchaSettings: CaptchaSettings,
                       reCaptchaAttemptService: ReCaptchaAttemptService, loginAttemptService: LoginAttemptService, restTemplate: RestOperations
) : AbstractCaptchaService(captchaSettings, reCaptchaAttemptService, loginAttemptService, restTemplate) {

    @Throws(ReCaptchaInvalidException::class)
    override fun processResponse(response: String, action: String) {
        securityCheck(response)

        val verifyUri =
            URI.create(java.lang.String.format(RECAPTCHA_URL_TEMPLATE, reCaptchaSecret, response, loginAttemptService.clientIP()))
        try {
            val googleResponse: GoogleResponse? = restTemplate.getForObject(verifyUri, GoogleResponse::class.java)
            LOGGER.debug("Google's response: {} ", googleResponse.toString())

            if (true != googleResponse?.success || !googleResponse.action
                    .equals(action) || googleResponse.score < captchaSettings.threshold
            ) {
                if (googleResponse?.hasClientError() == true) {
                    reCaptchaAttemptService.reCaptchaFailed(loginAttemptService.clientIP())
                }
                throw ReCaptchaInvalidException("reCaptcha was not successfully validated")
            }
        } catch (rce: RestClientException) {
            throw ReCaptchaUnavailableException("Registration unavailable at this time.  Please try again later.", rce)
        }
        reCaptchaAttemptService.reCaptchaSucceeded(loginAttemptService.clientIP())
    }

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(CaptchaServiceV3::class.java)

        const val REGISTER_ACTION: String = "register"
    }
}