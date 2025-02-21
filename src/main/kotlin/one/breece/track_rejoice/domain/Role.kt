package one.breece.track_rejoice.domain

import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import java.util.HashSet

@Entity
class Role(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @Column(nullable = false, unique = true)
    val name: String,
    @ManyToMany(mappedBy = "authorities" )
    val appUsers: MutableSet<AppUser> = HashSet()
):GrantedAuthority {
    override fun getAuthority(): String {
        return name
    }
}