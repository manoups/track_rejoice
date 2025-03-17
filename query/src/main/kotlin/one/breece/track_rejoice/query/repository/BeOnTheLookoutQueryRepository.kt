package one.breece.track_rejoice.query.repository

import one.breece.track_rejoice.core.domain.BoloStates
import one.breece.track_rejoice.query.domain.BeOnTheLookOut
import org.locationtech.jts.geom.Point
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface BeOnTheLookoutQueryRepository: ReadOnlyRepository<BeOnTheLookOut, Long> {
    @Query(
        """
            select bolo from BeOnTheLookOut bolo where bolo.state=:state and st_dwithin(bolo.lastSeenLocation, :point, :distanceInMeters)
        """,
        countQuery = """
            select count(bolo) from BeOnTheLookOut bolo where bolo.state=:state and st_dwithin(bolo.lastSeenLocation, :point, :distanceInMeters)
        """,
    )
    fun findIdsByLngLat(point: Point, distanceInMeters: Double, state: BoloStates, pageable: Pageable): Page<BeOnTheLookOut>
}