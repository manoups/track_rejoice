package one.breece.track_rejoice.repository.command

import one.breece.track_rejoice.domain.command.Item
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ItemRepository : CrudRepository<Item, Long>
