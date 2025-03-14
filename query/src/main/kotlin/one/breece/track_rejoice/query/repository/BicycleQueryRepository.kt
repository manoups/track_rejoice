package one.breece.track_rejoice.query.repository

import one.breece.track_rejoice.query.domain.Bicycle
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface BicycleQueryRepository:ReadOnlyRepository<Bicycle, Long> {
    fun readBySku(sku: UUID): Optional<Bicycle>
}
