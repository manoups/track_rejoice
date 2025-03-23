package one.breece.track_rejoice.security.domain

import jakarta.persistence.*
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import one.breece.track_rejoice.security.converters.AESEncryptionConverter
import org.springframework.security.oauth2.core.user.OAuth2User

@Entity
class AppUser(
    @NotBlank
    private var password: String,
    @NotBlank
    @Email
    @Convert(converter = AESEncryptionConverter::class)
    @Column(nullable = false, unique = true)
    private val username: String,
    @NotBlank
    @Convert(converter = AESEncryptionConverter::class)
    val firstName: String,
    @NotBlank
    @Convert(converter = AESEncryptionConverter::class)
    val lastName: String,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @NotEmpty
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
    var accountNonExpired: Boolean = true,
    var accountNonLocked: Boolean = true,
    var credentialsNonExpired: Boolean = true,
    var enabled: Boolean = false,
    override val isUsing2FA: Boolean = false,
    @Enumerated(EnumType.STRING)
    override val provider: Provider = Provider.LOCAL
) : AppUserDetails, OAuth2User {
    override fun getAttributes(): Map<String, Any> = emptyMap()
    override fun getName(): String = username

    override fun getAuthorities(): MutableSet<Role> = authorities

    override fun getPassword(): String = password

    fun setPassword(newPassword: String) {
        password = newPassword
    }

    override fun getUsername(): String = username.trim().lowercase()

    override fun isEnabled() = enabled

    override fun isAccountNonExpired() = accountNonExpired
    override fun isAccountNonLocked() = accountNonLocked
}