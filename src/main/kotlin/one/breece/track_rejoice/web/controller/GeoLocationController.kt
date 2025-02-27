package one.breece.track_rejoice.web.controller

import one.breece.track_rejoice.commands.APBCommand
import one.breece.track_rejoice.commands.AddressCommand
import one.breece.track_rejoice.service.GeocodingService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping(value = ["/geolocation"])
class GeoLocationController(private val geocodingService: GeocodingService) {

    @RequestMapping
    fun geoLocation(@RequestParam lat: Double, @RequestParam lon: Double, model: Model): String {
        val reverseGeocode = geocodingService.reverseGeocode(lat, lon)
        model.addAttribute("APBCommand",  APBCommand(address = reverseGeocode?:AddressCommand()))
        model.addAttribute("action", "create")
        return "checkoutform"
    }
}