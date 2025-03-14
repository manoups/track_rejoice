package one.breece.track_rejoice.command.repository

import one.breece.track_rejoice.command.domain.Pet
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface PetRepository : CrudRepository<Pet, Long> {

    @Query(
        """
            SELECT ST_Distance((SELECT q1.location from trace q1 WHERE   all_points_bulletin_id=:id1), (SELECT q2.location from trace q2 WHERE   all_points_bulletin_id=:id2)) 
        """, nativeQuery = true
    )
    fun findDistanceBetween(id1: Long, id2: Long): Double
    fun findBySku(sku: UUID): Optional<Pet>
}