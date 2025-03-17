package one.breece.track_rejoice.core.projections

import java.time.LocalDateTime

class BeOnTheLookOutProj (
    val shortDescription: String,
    val color: String? = null,
    val lastSeenLocation: List<*>,
    val geometryType: String,
    val lastSeenDate: LocalDateTime,
    val sku: String? = null,
    var detailsUrl: String
)
