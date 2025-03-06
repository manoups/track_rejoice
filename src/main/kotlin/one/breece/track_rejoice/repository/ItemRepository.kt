package one.breece.track_rejoice.repository

import one.breece.track_rejoice.domain.Item
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ItemRepository:CrudRepository<Item, Long> {

}
