package one.breece.track_rejoice.repository.command

import one.breece.track_rejoice.domain.command.Item
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ItemRepository : CrudRepository<Item, Long> {
    fun findBySku(sku: UUID): Optional<Item>
}
