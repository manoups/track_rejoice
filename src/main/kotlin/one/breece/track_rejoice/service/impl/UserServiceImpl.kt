package one.breece.track_rejoice.service.impl

import jakarta.transaction.Transactional
import one.breece.track_rejoice.commands.UserCommand
import one.breece.track_rejoice.domain.command.AppUser
import one.breece.track_rejoice.domain.command.Role
import one.breece.track_rejoice.domain.command.VerificationToken
import one.breece.track_rejoice.repository.command.RoleRepository
import one.breece.track_rejoice.repository.command.UserRepository
import one.breece.track_rejoice.repository.command.VerificationTokenRepository
import one.breece.track_rejoice.service.LoginAttemptService
import one.breece.track_rejoice.service.UserService
import one.breece.track_rejoice.web.dto.AppUserDetails
import one.breece.track_rejoice.web.error.UserAlreadyExistException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserServiceImpl(
    private val repository: UserRepository,
    private val roleRepository: RoleRepository,
    private val passwordEncoder: PasswordEncoder,
    private val loginAttemptService: LoginAttemptService,
    private val tokenRepository: VerificationTokenRepository
) :
    UserService, UserDetailsService {

    companion object {
        const val ROLE_USER = "ROLE_USER"
    }

    @Transactional
    override fun saveUser(userCommand: UserCommand): AppUserDetails {
        if (emailExists(userCommand.email!!)) {
            throw UserAlreadyExistException("User exists already")
        }
        val appUser = AppUser(
            passwordEncoder.encode(userCommand.password),
            userCommand.email.trim().lowercase(),
            userCommand.firstName!!,
            userCommand.lastName!!
        )
        val roleOptional = roleRepository.findByName(ROLE_USER)
        val role: Role = roleOptional.orElseThrow { RuntimeException("Role $ROLE_USER not found in the DB") }
        appUser.authorities.add(role)
        return repository.save(appUser)
    }

    override fun findUserByEmail(email: String): Optional<AppUserDetails> {
        return repository.findByUsername(email)
    }

    override fun findByEmail(email: String): Optional<AppUser> {
        return repository.getByUsername(email)
    }

    override fun findAllUsers(): List<AppUserDetails> {
        return repository.findAllUsers()
    }

    override fun createVerificationTokenForUser(userDetails: AppUserDetails, token: String) {
        val user = repository.getByUsername(userDetails.username)
            .orElseThrow { UsernameNotFoundException("User not found") }
        tokenRepository.save(VerificationToken(token = token, user = user))
    }

    @Transactional
    override fun validateVerificationToken(token: String): Pair<TokenEnum, UserDetails?> {
        val verificationTokenOptional = tokenRepository.findByToken(token)
        if (verificationTokenOptional.isEmpty)
            return Pair(TokenEnum.INVALID, null)
        return validateVerificationToken(verificationTokenOptional.get())
    }

    private fun validateVerificationToken(verificationToken: VerificationToken): Pair<TokenEnum, UserDetails?> {
        val user = verificationToken.user
        val cal = Calendar.getInstance()
        if ((verificationToken.expiryDate.time - cal.time.time) <= 0) {
//            Do not delete token here otherwise the user can end up with neither validation nor token in the DB
//            tokenRepository.delete(verificationToken)
            return Pair(TokenEnum.EXPIRED, null)
        }
        user.enabled = true
        tokenRepository.delete(verificationToken)
        repository.save(user)
        return Pair(TokenEnum.VALID, user)
    }

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(email: String): UserDetails {
        if (loginAttemptService.isBlocked) {
            throw UsernameNotFoundException("blocked")
        }
        return repository.findByUsername(email.trim().lowercase()).orElseThrow { UsernameNotFoundException("User not found: $email") }
    }

    override fun createUser(user: UserDetails?) {
        TODO("Not yet implemented")
    }

    override fun updateUser(user: UserDetails?) {
        TODO("Not yet implemented")
    }

    override fun deleteUser(username: String) {
        repository.getByUsername(username).ifPresent(repository::delete)
    }

    override fun changePassword(oldPassword: String?, newPassword: String?) {
        TODO("Not yet implemented")
    }

    override fun userExists(username: String): Boolean {
        return repository.findByUsername(username).isPresent
    }

    override fun updatePassword(user: UserDetails, newPassword: String): UserDetails? {
        val findByEmail = repository.getByUsername(user.username)
        findByEmail.ifPresent {
            it.password = passwordEncoder.encode(newPassword)
            repository.save(it)
        }
        return repository.findByUsername(user.username).orElse(null)
    }

    private fun emailExists(email: String): Boolean {
        return repository.findByUsername(email).isPresent
    }
}