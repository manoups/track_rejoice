package one.breece.track_rejoice.repository.command

import one.breece.track_rejoice.domain.command.Bicycle
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface BicycleRepository:CrudRepository<Bicycle, Long> {
    fun findBySku(sku: UUID):Optional<Bicycle>
}
