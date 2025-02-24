package one.breece.track_rejoice.web.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class GeocodingCoDTO(val address: AddressDTO)

@JsonIgnoreProperties(ignoreUnknown = true)
data class AddressDTO(
    @JsonProperty(value = "house_number")
    val houseNumber: Short?,
    val road: String?,
    val quarter: String?,
    val town: String?,
    val country: String?,
    val postcode: String?,
)