package one.breece.track_rejoice.service

import one.breece.track_rejoice.commands.UserCommand
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsPasswordService
import org.springframework.security.provisioning.UserDetailsManager
import java.util.*

interface UserService: UserDetailsManager, UserDetailsPasswordService {
    fun saveUser(userCommand: UserCommand)

    fun findUserByEmail(email: String): Optional<UserDetails>

    fun findAllUsers(): List<UserDetails>
}