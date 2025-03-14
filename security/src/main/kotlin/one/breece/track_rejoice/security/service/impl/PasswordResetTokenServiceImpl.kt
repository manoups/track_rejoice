package one.breece.track_rejoice.security.service.impl

import jakarta.transaction.Transactional
import one.breece.track_rejoice.security.command.PasswordChangeCommand
import one.breece.track_rejoice.security.domain.PasswordResetToken
import one.breece.track_rejoice.security.domain.AppUser
import one.breece.track_rejoice.security.repository.PasswordResetTokenRepository
import one.breece.track_rejoice.security.service.PasswordResetTokenService
import one.breece.track_rejoice.security.service.TokenEnum
import one.breece.track_rejoice.security.service.UserService
import one.breece.track_rejoice.security.util.GenericResponse
import org.springframework.context.MessageSource
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.util.*

@Service
class PasswordResetTokenServiceImpl(
    private val messages: MessageSource,
    val repository: PasswordResetTokenRepository,
    private val userService: UserService
) : PasswordResetTokenService {
    override fun createPasswordResetTokenForUser(email: String): PasswordResetToken {
        val user = userService.findByEmail(email).orElseThrow { UsernameNotFoundException("User $email not found") }
        val tokenOptional = repository.findByUser(user)
        if (tokenOptional.isEmpty) {
            val myToken = PasswordResetToken(UUID.randomUUID().toString(), user)
            return repository.save(myToken)
        } else {
            val resetToken = tokenOptional.get()
            resetToken.updateExpiryDate()
            return repository.save(resetToken)
        }
    }

    override fun validatePasswordResetToken(token: String): TokenEnum {
        val passToken = repository.findByToken(token)
        return if (passToken.isEmpty) {
            TokenEnum.INVALID
        } else if (isTokenExpired(passToken.get())) {
            TokenEnum.EXPIRED
        } else {
            TokenEnum.VALID
        }
    }

    override fun getUserByPasswordResetToken(token: String): Optional<AppUser> {
        return repository.findByToken(token).map { it.user }
    }

    @Transactional
    override fun savePassword(passwordChangeCommand: PasswordChangeCommand, locale: Locale): GenericResponse {
        val result = validatePasswordResetToken(passwordChangeCommand.token!!)

        if (result != TokenEnum.VALID) {
            return GenericResponse(messages.getMessage("auth.message.${result.toString().lowercase()}", null, locale))
        }
        val token = repository.findByToken(passwordChangeCommand.token!!).get()
        val user = token.user
        userService.updatePassword(user, passwordChangeCommand.newPassword)
        repository.delete(token)
        return GenericResponse(messages.getMessage("message.resetPasswordSuc", null, locale))
    }

    private fun isTokenExpired(passToken: PasswordResetToken): Boolean {
        val cal = Calendar.getInstance()
        return passToken.expiryDate.before(cal.time)
    }
}