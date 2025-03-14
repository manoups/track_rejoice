package one.breece.track_rejoice.query.repository

import one.breece.track_rejoice.core.domain.BoloStateEnum
import one.breece.track_rejoice.query.domain.Pet
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface PetQueryRepository:ReadOnlyRepository<Pet, Long> {
    fun readBySkuAndState(sku: UUID, state: BoloStateEnum): Optional<Pet>

}