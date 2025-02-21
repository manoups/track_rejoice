package one.breece.track_rejoice.domain

import jakarta.persistence.*
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import org.springframework.security.core.userdetails.UserDetails

@Entity
class AppUser(
    @NotBlank
    private var password: String,
    @NotBlank
    @Email
    private val username: String,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "users_roles",
        joinColumns = [JoinColumn(name = "USER_ID", referencedColumnName = "id")],
        inverseJoinColumns = [
            JoinColumn(
                name = "ROLE_ID",
                referencedColumnName = "id"
            )
        ]
    )
    private val authorities: MutableSet<Role> = HashSet(),
    val accountNonExpired: Boolean = false,
    val accountNonLocked: Boolean = false,
    val credentialsNonExpired: Boolean = false,
    val enabled: Boolean = false,
    val isUsing2FA: Boolean = false,
    @Enumerated(EnumType.STRING)
    val provider: Provider = Provider.LOCAL
) : UserDetails {
    override fun getAuthorities(): MutableSet<Role> {
        return authorities
            /*.map {
            object : GrantedAuthority {
                override fun getAuthority(): String = it.authority
                override fun toString() = it.authority
            }
        }.toMutableSet()*/
    }

    override fun getPassword(): String {
        return password
    }

    fun setPassword(newPassword: String) {
        password = newPassword
    }

    override fun getUsername(): String {
        return username
    }
}