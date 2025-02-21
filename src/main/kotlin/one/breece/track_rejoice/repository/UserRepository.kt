package one.breece.track_rejoice.repository

import one.breece.track_rejoice.domain.AppUser
import one.breece.track_rejoice.dto.AppUserDetails
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository: CrudRepository<AppUser, Long> {
    fun getByUsername(email: String): Optional<AppUser>
    fun findByUsername(email: String): Optional<AppUserDetails>
    @Query("select u from AppUser u")
    fun findAllUsers(): List<AppUserDetails>
}