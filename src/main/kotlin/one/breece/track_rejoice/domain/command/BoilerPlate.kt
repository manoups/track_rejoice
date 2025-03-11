package one.breece.track_rejoice.domain.command

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.util.*

@MappedSuperclass
abstract class BoilerPlate(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Version
    val version: Short = 0,
    @CreationTimestamp
    val createdAt: Date? = null,
    @UpdateTimestamp
    val updatedAt: Date? = null,
)
