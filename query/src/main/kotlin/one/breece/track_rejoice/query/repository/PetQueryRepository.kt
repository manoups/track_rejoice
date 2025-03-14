package one.breece.track_rejoice.query.repository

import one.breece.track_rejoice.query.domain.Pet
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface PetQueryRepository:ReadOnlyRepository<Pet, Long> {
    fun readBySku(sku: UUID): Optional<Pet>

}