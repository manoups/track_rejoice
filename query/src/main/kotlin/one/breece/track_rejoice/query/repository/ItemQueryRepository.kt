package one.breece.track_rejoice.query.repository

import one.breece.track_rejoice.query.domain.Item
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ItemQueryRepository : ReadOnlyRepository<Item, Long> {
    fun readBySku(sku: UUID):Optional<Item>
}