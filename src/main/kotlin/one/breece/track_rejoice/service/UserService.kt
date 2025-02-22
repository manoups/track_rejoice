package one.breece.track_rejoice.service

import one.breece.track_rejoice.commands.UserCommand
import one.breece.track_rejoice.web.dto.AppUserDetails
import org.springframework.security.core.userdetails.UserDetailsPasswordService
import org.springframework.security.provisioning.UserDetailsManager
import java.util.*

interface UserService: UserDetailsManager, UserDetailsPasswordService {
    fun saveUser(userCommand: UserCommand): AppUserDetails

    fun findUserByEmail(email: String): Optional<AppUserDetails>

    fun findAllUsers(): List<AppUserDetails>
    fun createVerificationTokenForUser(user: AppUserDetails, token: String)
}