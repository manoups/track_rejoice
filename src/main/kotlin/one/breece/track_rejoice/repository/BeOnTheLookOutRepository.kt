package one.breece.track_rejoice.repository

import one.breece.track_rejoice.domain.BeOnTheLookOut
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface BeOnTheLookOutRepository:CrudRepository<BeOnTheLookOut, Long> {
    @Query(
        """
            SELECT bolo.*
            FROM be_on_the_look_out bolo left outer join pet p ON p.id=bolo.id
            left outer join item it ON it.id=bolo.id
            WHERE bolo.enabled AND (ST_DWithin(p.last_seen_location, ST_SetSRID(ST_MakePoint(:lon, :lat), 4326), :distanceInMeters) OR
            ST_DWithin(it.last_seen_location, ST_SetSRID(ST_MakePoint(:lon, :lat), 4326), :distanceInMeters))
        """,
        nativeQuery = true,
        countQuery = """
            SELECT count(distinct p.id)
           FROM be_on_the_look_out bolo left outer join pet p ON p.id=bolo.id
            left outer join item it ON it.id=bolo.id
            WHERE bolo.enabled AND (ST_DWithin(p.last_seen_location, ST_SetSRID(ST_MakePoint(:lon, :lat), 4326), :distanceInMeters) OR
            ST_DWithin(it.last_seen_location, ST_SetSRID(ST_MakePoint(:lon, :lat), 4326), :distanceInMeters))
        """,
    )
    fun findAllByLngLat(lon: Double, lat: Double, distanceInMeters: Double, pageable: Pageable): Page<BeOnTheLookOut>

}