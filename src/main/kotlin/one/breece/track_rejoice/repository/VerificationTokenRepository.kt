package one.breece.track_rejoice.repository

import one.breece.track_rejoice.domain.VerificationToken
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface VerificationTokenRepository: CrudRepository<VerificationToken, Long> {
    fun findByToken(token: String): Optional<VerificationToken>
}