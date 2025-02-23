package one.breece.track_rejoice.web.controller

import one.breece.track_rejoice.service.PetService
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam


@Controller
class PetController(private val petService: PetService) {

    @GetMapping("/")
    fun search(@RequestParam(defaultValue = "0.0") lng: Double,
               @RequestParam(defaultValue = "0.0") lat: Double,
               @RequestParam(name = "distance", defaultValue = "0.0001") distanceInMeters: Double,
               pageable: Pageable,
               model: Model): String {
        model.addAttribute("pets", petService.findAllByLngLat(lng, lat, distanceInMeters, pageable).content)
        model.addAttribute("centre_lng", lng)
        model.addAttribute("centre_lat", lat)
        return "index"
    }

    @PostMapping("/search")
    fun searchQ(@RequestParam lng: Double,
                @RequestParam lat: Double,
                @RequestParam(name = "distance", defaultValue = "1") distanceInMeters: Double,
                @RequestParam(name = "zoom", defaultValue = "7") zoom: Int,
                pageable: Pageable,
                model: Model): String {
        model.addAttribute("pets", petService.findAllByLngLat(lng, lat, distanceInMeters, pageable).content)
        model.addAttribute("centre_lng", lng)
        model.addAttribute("centre_lat", lat)
        model.addAttribute("zoom", zoom)
        return "index"
    }
}