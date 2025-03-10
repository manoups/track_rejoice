package one.breece.track_rejoice.repository

import jakarta.persistence.Tuple
import one.breece.track_rejoice.domain.BeOnTheLookOut
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface BeOnTheLookOutRepository:JpaRepository<BeOnTheLookOut, Long> {
    @Query(
        """
            
            select case when(p.id is null) then it.short_description else p.name end,
       case when(p.id is null) then it.color else p.color end,
       case when(p.id is null) then it.last_seen_location else p.last_seen_location end, last_seen_date
            FROM be_on_the_look_out bolo left outer join pet p ON p.id=bolo.id
            left outer join item it ON it.id=bolo.id
            WHERE bolo.enabled AND (ST_DWithin(p.last_seen_location, ST_SetSRID(ST_MakePoint(:lon, :lat), 4326), :distanceInMeters) OR
            ST_DWithin(it.last_seen_location, ST_SetSRID(ST_MakePoint(:lon, :lat), 4326), :distanceInMeters))
        """,
        nativeQuery = true,
        countQuery = """
            SELECT count(distinct bolo.id)
           FROM be_on_the_look_out bolo left outer join pet p ON p.id=bolo.id
            left outer join item it ON it.id=bolo.id
            WHERE bolo.enabled AND (ST_DWithin(p.last_seen_location, ST_SetSRID(ST_MakePoint(:lon, :lat), 4326), :distanceInMeters) OR
            ST_DWithin(it.last_seen_location, ST_SetSRID(ST_MakePoint(:lon, :lat), 4326), :distanceInMeters))
        """,
    )
    fun findIdByLngLat(lon: Double, lat: Double, distanceInMeters: Double, pageable: Pageable): Page<Tuple>

    @Query(
        """
            
            select bolo.id
            FROM be_on_the_look_out bolo left outer join pet p ON p.id=bolo.id
            left outer join item it ON it.id=bolo.id
            left outer join bicycle b ON b.id=bolo.id
            WHERE bolo.enabled AND (ST_DWithin(p.last_seen_location, ST_SetSRID(ST_MakePoint(:lon, :lat), 4326), :distanceInMeters) OR
            ST_DWithin(it.last_seen_location, ST_SetSRID(ST_MakePoint(:lon, :lat), 4326), :distanceInMeters) OR 
             ST_DWithin(b.last_seen_location, ST_SetSRID(ST_MakePoint(:lon, :lat), 4326), :distanceInMeters))
        """,
        nativeQuery = true,
        countQuery = """
            SELECT count(distinct bolo.id)
           FROM be_on_the_look_out bolo left outer join pet p ON p.id=bolo.id
            left outer join item it ON it.id=bolo.id
            left outer join bicycle b ON b.id=bolo.id
            WHERE bolo.enabled AND (ST_DWithin(p.last_seen_location, ST_SetSRID(ST_MakePoint(:lon, :lat), 4326), :distanceInMeters) OR
            ST_DWithin(it.last_seen_location, ST_SetSRID(ST_MakePoint(:lon, :lat), 4326), :distanceInMeters) OR 
             ST_DWithin(b.last_seen_location, ST_SetSRID(ST_MakePoint(:lon, :lat), 4326), :distanceInMeters))
        """,
    )
    fun findIdsByLngLat(lon: Double, lat: Double, distanceInMeters: Double, pageable: Pageable): Page<Long>

    fun findBySku(sku: UUID): Optional<BeOnTheLookOut>

}