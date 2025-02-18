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
            FROM pet p join lookup_subject l ON p.id=l.id
            WHERE ST_DWithin(l.last_seen_location, ST_SetSRID(ST_MakePoint(:lng, :lat), 4326), :distanceInMeters)
        """,
        nativeQuery = true,
        countQuery = """
            SELECT count(distinct p.id)
            FROM lookup_subject p
            WHERE ST_DWithin(p.last_seen_location, ST_SetSRID(ST_MakePoint(:lng, :lat), 4326), :distanceInMeters)
        """,
    )
    fun findAllByLngLat(lng: Double, lat: Double, distanceInMeters: Double, pageable: Pageable): Page<Pet>

    @Query(
        """
            SELECT ST_Distance((SELECT q1.location from trace q1 WHERE lookup_subject_id=:id1), (SELECT q2.location from trace q2 WHERE lookup_subject_id=:id2)) 
        """, nativeQuery = true
    )
    fun findDistanceBetween(id1: Long, id2: Long): Double
}