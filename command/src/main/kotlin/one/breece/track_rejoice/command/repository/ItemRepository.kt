package one.breece.track_rejoice.command.repository

import one.breece.track_rejoice.command.domain.Item
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ItemRepository : CrudRepository<Item, Long> {
    fun findBySku(sku: UUID): Optional<Item>
}
