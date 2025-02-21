package one.breece.track_rejoice.service.impl

import jakarta.persistence.EntityManager
import jakarta.transaction.Transactional
import one.breece.track_rejoice.domain.Role
import one.breece.track_rejoice.domain.AppUser
import one.breece.track_rejoice.commands.UserCommand
import one.breece.track_rejoice.repository.RoleRepository
import one.breece.track_rejoice.repository.UserRepository
import one.breece.track_rejoice.service.UserService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*
import kotlin.jvm.optionals.getOrNull

@Service
class UserServiceImpl(
    private val repository: UserRepository,
    private val roleRepository: RoleRepository,
    private val passwordEncoder: PasswordEncoder,
    private val entityManager: EntityManager
) :
    UserService , UserDetailsService {
    @Transactional
    override fun saveUser(userCommand: UserCommand) {
        val appUser = AppUser(passwordEncoder.encode(userCommand.password), userCommand.email!!)
        val roleOptional = roleRepository.findByName("ROLE_ADMIN")
        entityManager.flush()
        var role: Role? = roleOptional.getOrNull()
        if (role == null) {
            role = checkRoleExist()
        }
        appUser.roles.add(role)
        repository.save(appUser)
    }

    override fun findUserByEmail(email: String): Optional<UserDetails> {
        return repository.findByEmail(email)
    }

    override fun findAllUsers(): List<UserDetails> {
        return repository.findAllUsers()
    }

    override fun loadUserByUsername(email: String): UserDetails? {
        return repository.findByEmail(email).orElse(null)
    }

    override fun createUser(user: UserDetails?) {
        TODO("Not yet implemented")
    }

    override fun updateUser(user: UserDetails?) {
        TODO("Not yet implemented")
    }

    override fun deleteUser(username: String?) {
        TODO("Not yet implemented")
    }

    override fun changePassword(oldPassword: String?, newPassword: String?) {
        TODO("Not yet implemented")
    }

    override fun userExists(username: String?): Boolean {
        TODO("Not yet implemented")
    }

    override fun updatePassword(user: UserDetails?, newPassword: String?): UserDetails {
        TODO("Not yet implemented")
    }

    private fun checkRoleExist(): Role {
        val role = Role(name = "ROLE_ADMIN")
        return roleRepository.saveAndFlush(role)
    }

}