package one.breece.track_rejoice.command.domain

import jakarta.persistence.Entity
import org.locationtech.jts.geom.Point

@Entity
class Bicycle(val color: String, val maker: String, val model: String, val year: Short,
                            phoneNumber: String,
                            lastSeenLocation: Point) :
    MobileItem(phoneNumber, lastSeenLocation)
