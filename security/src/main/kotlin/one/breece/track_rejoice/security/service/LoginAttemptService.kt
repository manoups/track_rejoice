package one.breece.track_rejoice.security.service

interface LoginAttemptService {
    fun loginFailed(key: String)
    val isBlocked: Boolean
    fun clientIP(): String
}