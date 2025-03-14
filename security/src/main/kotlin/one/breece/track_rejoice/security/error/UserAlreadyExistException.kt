package one.breece.track_rejoice.security.error

class UserAlreadyExistException(message: String? = null, cause: Throwable? = null) : RuntimeException(message, cause) {
    companion object {
        private const val serialVersionUID = 5861310537366287163L
    }
}
