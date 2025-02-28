package one.breece.track_rejoice.commands

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class UserPositionCommand (
    @field:NotNull
    val lon: Double? = null,
    @field:NotNull
    val lat: Double? = null,
    @field:NotNull
    val zoom: Int? = null,
    @field:NotBlank
    val target: String? = null,
    @field:NotNull
    val geoAvailable: Boolean? = null)
