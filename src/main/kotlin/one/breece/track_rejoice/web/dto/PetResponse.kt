package one.breece.track_rejoice.web.dto

import java.time.LocalDateTime


data class PetResponse(
    val id: Long,
    val name: String,
    val longitude: Double,
    val latitude: Double,
    val lastSeenDate: LocalDateTime,
//    val traceHistory: List<TraceDTO>
)