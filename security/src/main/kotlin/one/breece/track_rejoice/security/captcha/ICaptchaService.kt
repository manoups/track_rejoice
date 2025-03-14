package one.breece.track_rejoice.security.captcha

import one.breece.track_rejoice.security.error.ReCaptchaInvalidException


interface ICaptchaService {
    @Throws(ReCaptchaInvalidException::class)
    fun processResponse(response: String?) {
    }

    @Throws(ReCaptchaInvalidException::class)
    fun processResponse(response: String, action: String) {
    }

    val reCaptchaSite: String

    val reCaptchaSecret: String
}

