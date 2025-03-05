package one.breece.track_rejoice.web.dto

import one.breece.track_rejoice.commands.AddressCommand
import java.time.LocalDateTime


data class PetResponse(
    val id: Long,
    val name: String,
    val species: String,
    val longitude: Double,
    val latitude: Double,
    val lastSeenDate: LocalDateTime,
    val humanReadableAddress: AddressCommand?,
//    val traceHistory: List<TraceDTO>
)