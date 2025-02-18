package one.breece.track_rejoice.controller

import one.breece.track_rejoice.service.PetService
import org.springframework.data.domain.Pageable
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam


@Controller
class PetController(private val petService: PetService) {

    @GetMapping("/pets")
    fun greetings(@AuthenticationPrincipal principal: OAuth2AuthorizedClient?, model: Model): String {
        model.addAttribute("pets", petService.findAll())
        return "search"
    }

    @GetMapping("/search")
    fun search(@RequestParam lng: Double,
               @RequestParam lat: Double,
               @RequestParam(name = "distance") distanceInMeters: Double,
               pageable: Pageable,
               model: Model): String {
        model.addAttribute("pets", petService.findAllByLngLat(lng, lat, distanceInMeters, pageable).content)
        model.addAttribute("centre_lng", lng)
        model.addAttribute("centre_lat", lat)
        return "search"
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
        return "search"
    }

    @RequestMapping(path = ["", "/"])
    fun showPet(model: Model): String {
        val findById = petService.findById(1).get()
        model.addAttribute("pet", findById)
        return "index"
    }
}