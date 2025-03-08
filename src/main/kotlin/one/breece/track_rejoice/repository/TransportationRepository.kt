package one.breece.track_rejoice.repository

import one.breece.track_rejoice.domain.MeansOfTransportation
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface TransportationRepository:CrudRepository<MeansOfTransportation, Long> {
    @Query(
        """
            select * from means_of_transportation p join be_on_the_look_out bolo ON p.id=bolo.id where p.id in (:ids)
        """,
        nativeQuery = true
    )
    fun findAllByIdAnonymous(ids: Collection<Long>): List<MeansOfTransportation>

}
