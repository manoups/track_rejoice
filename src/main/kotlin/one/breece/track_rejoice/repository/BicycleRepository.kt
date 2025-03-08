package one.breece.track_rejoice.repository

import one.breece.track_rejoice.domain.Bicycle
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface BicycleRepository:CrudRepository<Bicycle, Long> {
    @Query(
        """
            select * from bicycle p join be_on_the_look_out bolo ON p.id=bolo.id where p.id in (:ids)
        """,
        nativeQuery = true
    )
    fun findAllByIdAnonymous(ids: Collection<Long>): List<Bicycle>

}
