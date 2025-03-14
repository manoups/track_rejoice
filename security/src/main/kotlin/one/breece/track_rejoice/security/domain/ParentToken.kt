package one.breece.track_rejoice.security.domain

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import java.util.*

@MappedSuperclass
class ParentToken (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,
    @NotBlank
    var token: String,
    @OneToOne(targetEntity = AppUser::class, fetch = FetchType.EAGER, optional =  false)
    @JoinColumn(nullable = false, name = "app_user_id", foreignKey = ForeignKey(name = "FK_VERIFY_USER"))
    val user: AppUser,
) {
    var expiryDate: Date = calculateExpiryDate(EXPIRATION)

    private fun calculateExpiryDate(expiryTimeInMinutes: Int): Date {
        val cal = Calendar.getInstance()
        cal.timeInMillis = Date().time
        cal.add(Calendar.MINUTE, expiryTimeInMinutes)
        return Date(cal.time.time)
    }

    fun updateExpiryDate() {
        expiryDate = calculateExpiryDate(EXPIRATION)
    }

    fun updateToken(token: String) {
        this.token = token
        this.expiryDate = calculateExpiryDate(EXPIRATION)
    }

    //
    override fun hashCode(): Int {
        val prime = 31
        var result = 1
        result = prime * result + (expiryDate?.hashCode()?:0)
        result = prime * result + (token?.hashCode()?:0)
        result = prime * result + (user?.hashCode()?:0)
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other == null) {
            return false
        }
        if (javaClass != other.javaClass) {
            return false
        }
        val parentToken = other as ParentToken
        if (expiryDate == null) {
            if (parentToken.expiryDate != null) {
                return false
            }
        } else if (expiryDate != parentToken.expiryDate) {
            return false
        }
        if (token == null) {
            if (parentToken.token != null) {
                return false
            }
        } else if (token != parentToken.token) {
            return false
        }
        if (user == null) {
            if (parentToken.user != null) {
                return false
            }
        } else if (user != parentToken.user) {
            return false
        }
        return true
    }

    override fun toString()="Token [String=$token][Expires$expiryDate]"

    companion object {
        private const val EXPIRATION = 60 * 24
    }
}
