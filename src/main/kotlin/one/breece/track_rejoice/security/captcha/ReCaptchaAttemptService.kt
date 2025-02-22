package one.breece.track_rejoice.security.captcha

import com.google.common.cache.CacheBuilder
import com.google.common.cache.CacheLoader
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

@Service
class ReCaptchaAttemptService {
    private val MAX_ATTEMPT = 4
    private val attemptsCache =
        CacheBuilder.newBuilder().expireAfterWrite(4, TimeUnit.HOURS).build(object : CacheLoader<String?, Int>() {
            override fun load(key: String): Int {
                return 0
            }
        })

    fun reCaptchaSucceeded(key: String) {
        attemptsCache.invalidate(key)
    }

    fun reCaptchaFailed(key: String) {
        var attempts = attemptsCache.getUnchecked(key)
        attempts++
        attemptsCache.put(key, attempts)
    }

    fun isBlocked(key: String): Boolean {
        return attemptsCache.getUnchecked(key) >= MAX_ATTEMPT
    }
}
