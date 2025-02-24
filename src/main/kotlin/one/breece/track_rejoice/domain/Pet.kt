package one.breece.track_rejoice.domain

import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.validation.constraints.NotNull
import org.locationtech.jts.geom.Point

@Entity
class Pet(
    @NotNull
    val name: String,
    lastSeenLocation: Point,
    @Enumerated(EnumType.STRING)
    val species: SpeciesEnum,
    @NotNull
    val breed: String,
    val color: String? = null,
    val transponderCode: Long? = null,
    var extraInformation: String? = null,
):AllPointsBulletin(lastSeenLocation, extraInfo=extraInformation)
