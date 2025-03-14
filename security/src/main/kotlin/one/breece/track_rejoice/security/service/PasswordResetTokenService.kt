package one.breece.track_rejoice.security.service

import one.breece.track_rejoice.security.command.PasswordChangeCommand
import one.breece.track_rejoice.security.domain.AppUser
import one.breece.track_rejoice.security.domain.PasswordResetToken
import one.breece.track_rejoice.security.util.GenericResponse
import java.util.*

interface PasswordResetTokenService {
    fun createPasswordResetTokenForUser(email: String): PasswordResetToken
    fun validatePasswordResetToken(token: String): TokenEnum
    fun getUserByPasswordResetToken(token: String): Optional<AppUser>
    fun savePassword(passwordChangeCommand: PasswordChangeCommand, locale: Locale): GenericResponse
}