package one.breece.track_rejoice.domain

import jakarta.persistence.Entity
import one.breece.track_rejoice.commands.AddressCommand
import org.locationtech.jts.geom.Point

@Entity
class MeansOfTransportation(val color: String, val brand: String, val model: String,
    lastSeenLocation: Point, humanReadableAddress: AddressCommand? = null) :
    MobileItem(lastSeenLocation, humanReadableAddress)
