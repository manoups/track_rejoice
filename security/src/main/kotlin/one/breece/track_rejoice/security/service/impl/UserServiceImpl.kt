package one.breece.track_rejoice.security.service.impl

import jakarta.transaction.Transactional
import one.breece.track_rejoice.security.command.UserCommand
import one.breece.track_rejoice.security.domain.*
import one.breece.track_rejoice.security.error.UserAlreadyExistException
import one.breece.track_rejoice.security.error.UserSignedInWithOAuthException
import one.breece.track_rejoice.security.repository.RoleRepository
import one.breece.track_rejoice.security.repository.UserRepository
import one.breece.track_rejoice.security.repository.VerificationTokenRepository
import one.breece.track_rejoice.security.service.LoginAttemptService
import one.breece.track_rejoice.security.service.TokenEnum
import one.breece.track_rejoice.security.service.UserService


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
    @Throws(UserAlreadyExistException::class, UserSignedInWithOAuthException::class)
    override fun saveUser(userCommand: UserCommand, provider: Provider): AppUserDetails {
        repository.findByUsername(userCommand.email!!).ifPresent {
            if (it.provider == Provider.GOOGLE) {
                throw UserSignedInWithOAuthException("User exists already with Google provider")
            } else
                throw UserAlreadyExistException("User exists already")
        }
        val appUser = createUser(userCommand, provider)
        val roleOptional = roleRepository.findByName(ROLE_USER)
        val role: Role = roleOptional.orElseThrow { RuntimeException("Role $ROLE_USER not found in the DB") }
        appUser.authorities.add(role)
        return repository.save(appUser)
    }

    @Throws(UsernameNotFoundException::class)
    override fun loadByUsernameAndProvider(username: String, provider: Provider): UserDetails {
        return repository.getByUsernameAndProvider(username, provider)
            .orElseThrow { UsernameNotFoundException("User not found") }
    }

    private fun createUser(userCommand: UserCommand, provider: Provider = Provider.LOCAL): AppUser {
        return AppUser(
            passwordEncoder.encode(userCommand.password),
            userCommand.email!!.trim().lowercase(),
            userCommand.firstName!!,
            userCommand.lastName!!,
            provider = provider
        )
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
        return repository.findByUsername(email.trim().lowercase())
            .orElseThrow { UsernameNotFoundException("User not found: $email") }
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
}