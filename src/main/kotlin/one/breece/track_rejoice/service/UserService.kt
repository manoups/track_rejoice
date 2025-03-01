package one.breece.track_rejoice.service

import one.breece.track_rejoice.commands.UserCommand
import one.breece.track_rejoice.domain.AppUser
import one.breece.track_rejoice.service.impl.TokenEnum
import one.breece.track_rejoice.web.dto.AppUserDetails
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsPasswordService
import org.springframework.security.provisioning.UserDetailsManager
import java.util.*

interface UserService: UserDetailsManager, UserDetailsPasswordService {
    fun saveUser(userCommand: UserCommand): AppUserDetails

    fun findUserByEmail(email: String): Optional<AppUserDetails>
    fun findByEmail(email: String): Optional<AppUser>
    fun findAllUsers(): List<AppUserDetails>
    fun createVerificationTokenForUser(userDetails: AppUserDetails, token: String)
    fun validateVerificationToken(token: String): Pair<TokenEnum, UserDetails?>
}