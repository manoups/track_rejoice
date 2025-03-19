package one.breece.track_rejoice.command.repository

import one.breece.track_rejoice.command.domain.BeOnTheLookOut
import one.breece.track_rejoice.core.domain.BoloStates
import org.springframework.context.annotation.Primary
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Primary
@Repository
interface BeOnTheLookOutRepository:JpaRepository<BeOnTheLookOut, Long> {

    fun findBySku(sku: UUID): Optional<BeOnTheLookOut>
    fun findAllByStateIn(boloStates: Collection<BoloStates>, pageable: Pageable): Page<BeOnTheLookOut>


}