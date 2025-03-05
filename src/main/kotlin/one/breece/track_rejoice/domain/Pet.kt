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
    @NotNull
    lastSeenLocation: Point,
    @NotNull
    @Enumerated(EnumType.STRING)
    val species: SpeciesEnum,
    @NotNull
    val breed: String,
    @NotNull
    @Enumerated(EnumType.STRING)
    val sex: PetSexEnum,
    val color: String? = null,
    val transponderCode: Long? = null,
) : MobileItem(lastSeenLocation)
