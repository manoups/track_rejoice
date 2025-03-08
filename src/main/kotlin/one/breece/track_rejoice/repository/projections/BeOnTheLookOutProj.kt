package one.breece.track_rejoice.repository.projections

import java.time.LocalDateTime

class BeOnTheLookOutProj (
    val name: String? = null,
    val type: String,
    val color: String? = null,
    val lastSeenLocation: List<*>,
    val geometryType: String,
    val lastSeenDate: LocalDateTime
)
