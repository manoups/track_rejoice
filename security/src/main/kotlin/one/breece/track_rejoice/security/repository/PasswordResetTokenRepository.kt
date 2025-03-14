package one.breece.track_rejoice.security.repository

import one.breece.track_rejoice.security.domain.AppUser
import one.breece.track_rejoice.security.domain.PasswordResetToken
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface PasswordResetTokenRepository: CrudRepository<PasswordResetToken, Long> {
    fun findByUser(user: AppUser): Optional<PasswordResetToken>
    fun findByToken(token: String): Optional<PasswordResetToken>

}
