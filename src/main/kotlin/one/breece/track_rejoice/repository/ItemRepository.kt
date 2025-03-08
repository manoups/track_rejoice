package one.breece.track_rejoice.repository

import one.breece.track_rejoice.domain.Item
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ItemRepository : CrudRepository<Item, Long> {
    @Query(
        """
            select * from item it join be_on_the_look_out bolo ON it.id=bolo.id where it.id in (:ids)
        """,
        nativeQuery = true
    )
    fun findAllByIdAnonymous(ids: List<Long>): List<Item>

}
