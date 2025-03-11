package one.breece.track_rejoice.domain.query

import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.validation.constraints.NotNull
import one.breece.track_rejoice.domain.PetSexEnum
import one.breece.track_rejoice.domain.SpeciesEnum
import org.locationtech.jts.geom.Point

@Entity
class Pet(
    @NotNull
    val name: String,
    lastSeenLocation: Point,
    phoneNumber: String,
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
) : MobileItem(phoneNumber, lastSeenLocation)