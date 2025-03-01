package one.breece.track_rejoice.repository

import one.breece.track_rejoice.domain.PasswordResetToken
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PasswordResetTokenRepository: JpaRepository<PasswordResetToken, Long> {

}
