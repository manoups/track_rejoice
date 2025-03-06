package one.breece.track_rejoice.domain

import jakarta.persistence.Entity
import jakarta.validation.constraints.NotNull
import org.locationtech.jts.geom.MultiPoint

@Entity
class Item(
    @NotNull
    lastSeenLocation: MultiPoint,
):ImmobileItem(lastSeenLocation)