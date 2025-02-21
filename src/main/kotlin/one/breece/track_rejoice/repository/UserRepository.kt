package one.breece.track_rejoice.repository

import one.breece.track_rejoice.domain.AppUser
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface UserRepository: CrudRepository<AppUser, Long> {
    fun findByEmail(email: String): Optional<UserDetails>
    @Query("select u from AppUser u")
    fun findAllUsers(): List<UserDetails>
}