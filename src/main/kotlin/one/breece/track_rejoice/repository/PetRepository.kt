package one.breece.track_rejoice.repository

import one.breece.track_rejoice.domain.Pet
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface PetRepository : CrudRepository<Pet, Long> {
    @Query(
        """
            SELECT *
            FROM pet p join be_on_the_look_out l ON p.id=l.id
            WHERE l.enabled AND ST_DWithin(p.last_seen_location, ST_SetSRID(ST_MakePoint(:lon, :lat), 4326), :distanceInMeters)
        """,
        nativeQuery = true,
        countQuery = """
            SELECT count(distinct p.id)
            FROM   pet p join be_on_the_look_out l ON p.id=l.id
            WHERE l.enabled AND ST_DWithin(p.last_seen_location, ST_SetSRID(ST_MakePoint(:lon, :lat), 4326), :distanceInMeters)
        """,
    )
    fun findAllByLngLat(lon: Double, lat: Double, distanceInMeters: Double, pageable: Pageable): Page<Pet>

    @Query(
        """
            SELECT ST_Distance((SELECT q1.location from trace q1 WHERE   all_points_bulletin_id=:id1), (SELECT q2.location from trace q2 WHERE   all_points_bulletin_id=:id2)) 
        """, nativeQuery = true
    )
    fun findDistanceBetween(id1: Long, id2: Long): Double

    @Query(
        """
            select * from pet p join be_on_the_look_out bolo ON p.id=bolo.id where p.id in (:ids)
        """,
        nativeQuery = true
    )
    fun findAllByIdAnonymous(ids: Collection<Long>): List<Pet>
}