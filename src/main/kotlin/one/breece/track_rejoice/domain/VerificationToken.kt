package one.breece.track_rejoice.domain

import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity

@Entity
@DiscriminatorValue("verification_token")
class VerificationToken(token: String, user: AppUser) : ParentToken(token = token, user = user)