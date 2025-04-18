package one.breece.track_rejoice.commands

import jakarta.validation.constraints.NotNull

data class UserPositionCommand (
    @field:NotNull
    val lng: Double? = null,
    @field:NotNull
    val lat: Double? = null,
    @field:NotNull
    val zoom: Int? = null,
    @field:NotNull
    val geoAvailable: Boolean? = null)
