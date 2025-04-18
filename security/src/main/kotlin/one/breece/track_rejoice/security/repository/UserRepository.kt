package one.breece.track_rejoice.security.repository

import one.breece.track_rejoice.security.domain.AppUser
import one.breece.track_rejoice.security.domain.AppUserDetails
import one.breece.track_rejoice.security.domain.Provider
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository: CrudRepository<AppUser, Long> {
    fun getByUsername(email: String): Optional<AppUser>
    fun findByUsername(email: String): Optional<AppUserDetails>
    @Query("select u from AppUser u")
    fun findAllUsers(): List<AppUserDetails>
    fun getByUsernameAndProvider(username: String, provider: Provider): Optional<UserDetails>
}