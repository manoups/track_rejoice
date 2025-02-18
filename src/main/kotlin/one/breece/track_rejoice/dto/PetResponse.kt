package one.breece.track_rejoice.dto

import java.util.*

data class PetResponse(
    val id: Long,
    val name: String,
    val longitude: Double,
    val latitude: Double,
    val lastSeenDate: Date,
//    val traceHistory: List<TraceDTO>
)