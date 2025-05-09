package one.breece.track_rejoice.security.service

import one.breece.track_rejoice.security.command.UserCommand
import one.breece.track_rejoice.security.domain.AppUser
import one.breece.track_rejoice.security.domain.AppUserDetails
import one.breece.track_rejoice.security.domain.Provider
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsPasswordService
import org.springframework.security.provisioning.UserDetailsManager
import java.util.*

interface UserService: UserDetailsManager, UserDetailsPasswordService {
    fun findUserByEmail(email: String): Optional<AppUserDetails>
    fun findByEmail(email: String): Optional<AppUser>
    fun findAllUsers(): List<AppUserDetails>
    fun createVerificationTokenForUser(userDetails: AppUserDetails, token: String)
    fun validateVerificationToken(token: String): Pair<TokenEnum, UserDetails?>
    fun saveUser(userCommand: UserCommand, provider: Provider = Provider.LOCAL): AppUserDetails
    fun loadByUsernameAndProvider(username: String, provider: Provider = Provider.LOCAL): UserDetails
}