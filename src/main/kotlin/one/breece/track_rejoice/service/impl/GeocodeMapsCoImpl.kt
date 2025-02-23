package one.breece.track_rejoice.service.impl

import one.breece.track_rejoice.commands.AddressCommand
import one.breece.track_rejoice.service.GeocodingService
import one.breece.track_rejoice.web.dto.CoordinateDTO
import org.locationtech.jts.geom.GeometryFactory
import org.locationtech.jts.geom.Point
import org.locationtech.jts.geom.impl.PackedCoordinateSequenceFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder


/** Forward Geocode (Convert human-readable address to coordinates):
https://geocode.maps.co/search?q=address&api_key=67bb5db6197e2623610027dpz0edd61

Reverse Geocode (Convert coordinates to human-readable address):
https://geocode.maps.co/reverse?lat=latitude&lon=longitude&api_key=67bb5db6197e2623610027dpz0edd61

Replace {address}, {latitude} and {longitude} with the values to geocode.
To "free form" search for an address, you can pass the address string using the q parameter to the /search endpoint, e.g.:

https://geocode.maps.co/search?q=555+5th+Ave+New+York+NY+10017+US&api_key=api_key

Or you can search by name, e.g. for the Statue of Liberty:

https://geocode.maps.co/search?q=Statue+of+Liberty+NY+US&api_key=api_key

However, you can also reverse geocode by providing the following parameters:

street=<housenumber> <streetname>
city=<city>
county=<county>
state=<state>
country=<country>
postalcode=<postalcode>
For example:

https://geocode.maps.co/search?street=555+5th+Ave&city=New+York&state=NY&postalcode=10017&country=US&api_key=api_key */
@Service
class GeocodeMapsCoImpl : GeocodingService {
    companion object {
        const val URI = "https://geocode.maps.co"
    }

    @Value("\${geocoding.key}")
    lateinit var apiKey: String

    override fun geocode(addressCommand: AddressCommand): Point? {
        val coordinatesFromAddress = getCoordinatesFromAddress(addressCommand)
        if (coordinatesFromAddress.isEmpty()) {
            return null
        }
        val gf = GeometryFactory()
        val sf = PackedCoordinateSequenceFactory()
        return gf.createPoint(sf.create(doubleArrayOf(coordinatesFromAddress[0].lon, coordinatesFromAddress[0].lat), 2))
    }

    private fun getCoordinatesFromAddress(addressCommand: AddressCommand): Array<CoordinateDTO> {
        val uriComponents = UriComponentsBuilder.fromUriString(URI)
            .path("search")
            .queryParam("street", "${addressCommand.streetNumber} ${addressCommand.street}".replace(" ", "+"))
            .queryParam("city", addressCommand.place)
            .queryParam("country", addressCommand.country)
            .queryParam("postalcode", addressCommand.zipCode)
            .queryParam("api_key", apiKey)
            .build().toUriString()
        val responseEntity = RestTemplate()
        return responseEntity.getForEntity(uriComponents, Array<CoordinateDTO>::class.java).body!!
    }
}