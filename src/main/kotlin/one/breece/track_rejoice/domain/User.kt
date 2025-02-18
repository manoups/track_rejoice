package one.breece.track_rejoice.domain

import jakarta.persistence.*

@Entity
class User (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = -1,
    var username: String,
    var password: String,
    @Enumerated(EnumType.STRING)
    val  provider: Provider)