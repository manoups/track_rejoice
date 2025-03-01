package one.breece.track_rejoice.service.impl

import one.breece.track_rejoice.domain.PasswordResetToken
import one.breece.track_rejoice.repository.PasswordResetTokenRepository
import one.breece.track_rejoice.service.PasswordResetTokenService
import one.breece.track_rejoice.service.UserService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.util.*

@Service
class PasswordResetTokenServiceImpl(
    val passwordTokenRepository: PasswordResetTokenRepository,
    private val userService: UserService) : PasswordResetTokenService {
    override fun createPasswordResetTokenForUser(email: String): PasswordResetToken {
        val user = userService.findByEmail(email).orElseThrow { UsernameNotFoundException("User $email not found") }
//        TODO: check for existing request token
//        TODO: delete existing token if invalid
        val myToken = PasswordResetToken(UUID.randomUUID().toString(), user)
        return passwordTokenRepository.save(myToken)
    }
}