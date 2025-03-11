package one.breece.track_rejoice.domain.command

import jakarta.persistence.Entity

@Entity
class VerificationToken(token: String, user: AppUser) : ParentToken(token = token, user = user)