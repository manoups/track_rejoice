package one.breece.track_rejoice.domain

import jakarta.annotation.Nonnull
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import org.locationtech.jts.geom.Point

@Entity
class Pet(
    @Nonnull
    val name: String,
    lastSeenLocation: Point,
    @Enumerated(EnumType.STRING)
    val species: SpeciesEnum,
    val breed: String,
    val color: String,
    val transponderCode: Int? = null,
):LookupSubject(lastSeenLocation)
