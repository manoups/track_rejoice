package one.breece.track_rejoice.service

import one.breece.track_rejoice.domain.PasswordResetToken

interface PasswordResetTokenService {
    fun createPasswordResetTokenForUser(email: String): PasswordResetToken
}