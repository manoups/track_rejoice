package one.breece.track_rejoice.security.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import kotlin.properties.Delegates


@Component
@ConfigurationProperties(prefix = "google.recaptcha.key")
class CaptchaSettings {
     lateinit var site: String
     lateinit var secret: String
     var threshold by Delegates.notNull<Float>()
}