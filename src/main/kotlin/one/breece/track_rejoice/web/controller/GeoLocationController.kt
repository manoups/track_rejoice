package one.breece.track_rejoice.web.controller

import one.breece.track_rejoice.commands.AddressCommand
import one.breece.track_rejoice.service.GeocodingService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/geolocation"])
class GeoLocationController(private val geocodingService: GeocodingService) {

    @GetMapping
    fun geoLocation(@RequestParam lat: Double, @RequestParam lon: Double): AddressCommand? {
        return geocodingService.reverseGeocode(lat, lon)
    }
}