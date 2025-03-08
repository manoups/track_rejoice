package one.breece.track_rejoice.domain

import jakarta.persistence.Entity
import jakarta.validation.constraints.NotNull
import org.locationtech.jts.geom.MultiPoint

@Entity
class Item(
    val shortDescription: String,
    val color: String? = null,
    phoneNumber: String,
    @NotNull
    lastSeenLocation: MultiPoint,
    ):ImmobileItem(phoneNumber, lastSeenLocation)