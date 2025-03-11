package one.breece.track_rejoice.repository.command

import one.breece.track_rejoice.domain.command.Role
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface RoleRepository: JpaRepository<Role, Long> {
    fun findByName(name: String): Optional<Role>
    fun findByAppUsers(of: Collection<UserDetails>): Set<Role>
}