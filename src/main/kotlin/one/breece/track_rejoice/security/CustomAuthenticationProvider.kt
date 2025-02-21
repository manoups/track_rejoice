package one.breece.track_rejoice.security

import one.breece.track_rejoice.repository.UserRepository
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException

//@Component
class CustomAuthenticationProvider(private val userRepository: UserRepository) : DaoAuthenticationProvider() {

    @Throws(AuthenticationException::class)
    override fun authenticate(auth: Authentication): Authentication {
        val optionalUser = userRepository.findByUsername(auth.name)
        if (optionalUser.isEmpty) {
            throw BadCredentialsException("Invalid username or password")
        }
        val user = optionalUser.get()
        // to verify verification code
        if (user.isUsing2FA) {
//            TODO(): not implemented yet
            /*val verificationCode: String = (auth.details as CustomWebAuthenticationDetails).getVerificationCode()
            val totp: Totp = Totp(user.getSecret())
            if (!isValidLong(verificationCode) || !totp.verify(verificationCode)) {
                throw BadCredentialsException("Invalid verification code")
            }*/
        }
        val result = super.authenticate(auth)
        return UsernamePasswordAuthenticationToken(user, result.credentials, result.authorities)
    }

    private fun isValidLong(code: String): Boolean {
        try {
            code.toLong()
        } catch (e: NumberFormatException) {
            return false
        }
        return true
    }

    override fun supports(authentication: Class<*>): Boolean {
        return authentication == UsernamePasswordAuthenticationToken::class.java
    }
}