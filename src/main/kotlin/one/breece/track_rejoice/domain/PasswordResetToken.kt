package one.breece.track_rejoice.domain

import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity

@Entity
@DiscriminatorValue("password_reset")
class PasswordResetToken(token: String, user: AppUser) : ParentToken(token = token, user = user)