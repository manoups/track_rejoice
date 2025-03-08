package one.breece.track_rejoice.repository

import one.breece.track_rejoice.domain.Bicycle
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface BicycleRepository:CrudRepository<Bicycle, Long>
