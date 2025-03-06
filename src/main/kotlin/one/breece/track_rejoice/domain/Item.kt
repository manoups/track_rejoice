package one.breece.track_rejoice.domain

import jakarta.persistence.Entity
import jakarta.validation.constraints.NotNull
import org.locationtech.jts.geom.MultiPoint

@Entity
class Item(
    val shortDescription: String? = null,
    val color: String? = null,
    @NotNull
    lastSeenLocation: MultiPoint,
):ImmobileItem(lastSeenLocation)