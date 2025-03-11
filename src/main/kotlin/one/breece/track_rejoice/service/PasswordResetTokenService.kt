package one.breece.track_rejoice.service

import one.breece.track_rejoice.commands.PasswordChangeCommand
import one.breece.track_rejoice.domain.command.AppUser
import one.breece.track_rejoice.domain.command.PasswordResetToken
import one.breece.track_rejoice.service.impl.TokenEnum
import one.breece.track_rejoice.util.GenericResponse
import java.util.*

interface PasswordResetTokenService {
    fun createPasswordResetTokenForUser(email: String): PasswordResetToken
    fun validatePasswordResetToken(token: String): TokenEnum
    fun getUserByPasswordResetToken(token: String): Optional<AppUser>
    fun savePassword(passwordChangeCommand: PasswordChangeCommand, locale: Locale): GenericResponse
}