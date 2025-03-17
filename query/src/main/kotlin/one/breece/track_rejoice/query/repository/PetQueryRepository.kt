package one.breece.track_rejoice.query.repository

import one.breece.track_rejoice.core.domain.BoloStates
import one.breece.track_rejoice.query.domain.Pet
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface PetQueryRepository:ReadOnlyRepository<Pet, Long> {
    fun readBySkuAndState(sku: UUID, state: BoloStates): Optional<Pet>

}