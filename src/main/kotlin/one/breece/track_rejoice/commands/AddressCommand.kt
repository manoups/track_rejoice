package one.breece.track_rejoice.commands

import jakarta.validation.constraints.NotBlank

data class AddressCommand (
    @field:NotBlank val street: String? = null,
    @field:NotBlank val streetNumber: Short? = null,
    @field:NotBlank val zipCode: String? = null,
    @field:NotBlank val place: String? = null,
    @field:NotBlank val country: String? = null,
)