package one.breece.track_rejoice.service

import com.google.common.cache.CacheBuilder
import com.google.common.cache.CacheLoader
import com.google.common.cache.LoadingCache
import jakarta.servlet.http.HttpServletRequest
import org.springframework.stereotype.Service
import java.util.concurrent.ExecutionException
import java.util.concurrent.TimeUnit

@Service
class LoginAttemptService(private val request: HttpServletRequest) {
    private val attemptsCache: LoadingCache<String, Int> =
        CacheBuilder.newBuilder().expireAfterWrite(1, TimeUnit.DAYS).build(object : CacheLoader<String, Int>() {
            override fun load(key: String): Int {
                return 0
            }
        })

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
                attemptsCache.get(clientIP()) >= MAX_ATTEMPT
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

    companion object {
        const val MAX_ATTEMPT: Int = 3
    }
}
