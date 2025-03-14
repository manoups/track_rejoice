package one.breece.track_rejoice.security.domain

import jakarta.persistence.Entity

@Entity
class PasswordResetToken(token: String, user: AppUser) : ParentToken(token = token, user = user)