package one.breece.track_rejoice.service.impl

import jakarta.persistence.EntityManager
import jakarta.transaction.Transactional
import one.breece.track_rejoice.commands.UserCommand
import one.breece.track_rejoice.domain.AppUser
import one.breece.track_rejoice.domain.Role
import one.breece.track_rejoice.domain.VerificationToken
import one.breece.track_rejoice.dto.AppUserDetails
import one.breece.track_rejoice.exception.UserAlreadyExistException
import one.breece.track_rejoice.repository.RoleRepository
import one.breece.track_rejoice.repository.UserRepository
import one.breece.track_rejoice.repository.VerificationTokenRepository
import one.breece.track_rejoice.service.LoginAttemptService
import one.breece.track_rejoice.service.UserService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*
import kotlin.jvm.optionals.getOrNull

@Service
class UserServiceImpl(
    private val repository: UserRepository,
    private val roleRepository: RoleRepository,
    private val passwordEncoder: PasswordEncoder,
    private val entityManager: EntityManager,
    private val loginAttemptService: LoginAttemptService,
    private val tokenRepository: VerificationTokenRepository,
    private val userRepository: UserRepository
) :
    UserService, UserDetailsService {

    @Transactional
    override fun saveUser(userCommand: UserCommand):AppUserDetails {
        if (emailExists(userCommand.email!!)) {
            throw UserAlreadyExistException("User exists already")
        }
        val appUser = AppUser(passwordEncoder.encode(userCommand.password), userCommand.email)
        val roleOptional = roleRepository.findByName("ROLE_ADMIN")
        entityManager.flush()
        var role: Role? = roleOptional.getOrNull()
        if (role == null) {
            role = checkRoleExist()
        }
        appUser.authorities.add(role)
        return repository.save(appUser)
    }

    override fun findUserByEmail(email: String): Optional<AppUserDetails> {
        return repository.findByUsername(email)
    }

    override fun findAllUsers(): List<AppUserDetails> {
        return repository.findAllUsers()
    }

    override fun createVerificationTokenForUser(userDetails: AppUserDetails, token: String) {
        val user = userRepository.getByUsername(userDetails.username).orElseThrow { UsernameNotFoundException("User not found") }
        val myToken: VerificationToken = VerificationToken(token = token, user = user)
        tokenRepository.save(myToken)    }

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(email: String): UserDetails {
        if (loginAttemptService.isBlocked) {
            throw UsernameNotFoundException("blocked")
        }
        return repository.findByUsername(email).orElseThrow { UsernameNotFoundException("User not found: $email") }
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

    private fun checkRoleExist(): Role {
        val role = Role(name = "ROLE_ADMIN")
        return roleRepository.saveAndFlush(role)
    }

    private fun emailExists(email: String): Boolean {
        return userRepository.findByUsername(email).isPresent
    }
}