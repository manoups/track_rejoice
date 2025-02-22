package one.breece.track_rejoice.service

import com.google.common.cache.LoadingCache
import jakarta.servlet.http.HttpServletRequest
import one.breece.track_rejoice.security.config.AttemptConfig
import org.springframework.stereotype.Service
import java.util.concurrent.ExecutionException

@Service
class LoginAttemptService(
    private val request: HttpServletRequest,
    private val attemptsCache: LoadingCache<String, Int>
) {

    fun loginFailed(key: String) {
        var attempts = try {
            attemptsCache.get(key)
        } catch (e: ExecutionException) {
            0
        }
        attempts++
        attemptsCache.put(key, attempts)
    }

    val isBlocked: Boolean
        get() {
            return try {
                attemptsCache.get(clientIP()) >= AttemptConfig.MAX_ATTEMPT
            } catch (e: ExecutionException) {
                false
            }
        }

    private fun clientIP(): String {
        val xfHeader = request.getHeader("X-Forwarded-For")
        if (xfHeader == null || xfHeader.isEmpty() || !xfHeader.contains(request.remoteAddr)) {
            return request.remoteAddr
        }
        return xfHeader.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]
    }
}
