package one.breece.track_rejoice.domain

import jakarta.persistence.Entity
import one.breece.track_rejoice.commands.AddressCommand
import org.locationtech.jts.geom.Point

@Entity
class Bicycle(val color: String, val maker: String, val model: String, val year: Short,
                            phoneNumber: String,
                            lastSeenLocation: Point, humanReadableAddress: AddressCommand? = null) :
    MobileItem(phoneNumber, lastSeenLocation, humanReadableAddress= humanReadableAddress)
