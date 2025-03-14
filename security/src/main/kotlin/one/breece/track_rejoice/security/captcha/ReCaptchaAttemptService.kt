package one.breece.track_rejoice.security.captcha

import com.google.common.cache.LoadingCache
import one.breece.track_rejoice.security.config.AttemptConfig
import org.springframework.stereotype.Service

@Service
class ReCaptchaAttemptService(private val attemptsCache: LoadingCache<String, Int>) {

    fun reCaptchaSucceeded(key: String) {
        attemptsCache.invalidate(key)
    }

    fun reCaptchaFailed(key: String) {
        var attempts = attemptsCache.getUnchecked(key)
        attempts++
        attemptsCache.put(key, attempts)
    }

    fun isBlocked(key: String): Boolean {
        return attemptsCache.getUnchecked(key) >= AttemptConfig.MAX_ATTEMPT
    }
}
