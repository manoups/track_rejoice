package one.breece.track_rejoice.dto

import org.locationtech.jts.geom.Point

data class CreatePetRequest(var name: String, var lastSeenLocation: Point, val species: String,
                            val breed: String,
                            val color: String,)
