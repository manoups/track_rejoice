package one.breece.track_rejoice.domain

import jakarta.persistence.*
import java.util.*

@Entity
class VerificationToken (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,
    var token: String? = null,
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

    fun updateToken(token: String?) {
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
        val verificationToken = other as VerificationToken
        if (expiryDate == null) {
            if (verificationToken.expiryDate != null) {
                return false
            }
        } else if (expiryDate != verificationToken.expiryDate) {
            return false
        }
        if (token == null) {
            if (verificationToken.token != null) {
                return false
            }
        } else if (token != verificationToken.token) {
            return false
        }
        if (user == null) {
            if (verificationToken.user != null) {
                return false
            }
        } else if (user != verificationToken.user) {
            return false
        }
        return true
    }

    override fun toString()="Token [String=$token][Expires$expiryDate]"

    companion object {
        private const val EXPIRATION = 60 * 24
    }
}
