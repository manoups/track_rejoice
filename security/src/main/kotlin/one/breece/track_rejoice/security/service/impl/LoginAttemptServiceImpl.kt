package one.breece.track_rejoice.security.service.impl

import com.google.common.cache.LoadingCache
import jakarta.servlet.http.HttpServletRequest
import one.breece.track_rejoice.security.config.AttemptConfig
import one.breece.track_rejoice.security.service.LoginAttemptService
import org.springframework.stereotype.Service
import java.util.concurrent.ExecutionException

/**
* Injecting the HttpServletRequest is possible according to
 * [stackoverflow][https://stackoverflow.com/questions/48574780/autowired-httpservletrequest-vs-passing-as-parameter-best-practice]
 * */
@Service
class LoginAttemptServiceImpl(
    private val request: HttpServletRequest,
    private val attemptsCache: LoadingCache<String, Int>
): LoginAttemptService {

    override fun loginFailed(key: String) {
        var attempts = try {
            attemptsCache.get(key)
        } catch (e: ExecutionException) {
            0
        }
        attempts++
        attemptsCache.put(key, attempts)
    }

    override val isBlocked: Boolean
        get() {
            return try {
                attemptsCache.get(clientIP()) >= AttemptConfig.MAX_ATTEMPT
            } catch (e: ExecutionException) {
                false
            }
        }

    override fun clientIP(): String {
        val xfHeader = request.getHeader("X-Forwarded-For")
        if (xfHeader == null || xfHeader.isEmpty() || !xfHeader.contains(request.remoteAddr)) {
            return request.remoteAddr
        }
        return xfHeader.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]
    }
}
