package one.breece.track_rejoice.web.controller

import one.breece.track_rejoice.domain.AppUser
import one.breece.track_rejoice.service.PetService
import org.springframework.data.domain.Pageable
import org.springframework.security.core.annotation.CurrentSecurityContext
import org.springframework.security.core.context.SecurityContext
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import java.util.*


@Controller
class PetController(private val petService: PetService) {

    @GetMapping("/")
    fun search(
    ): String {
        return "landing"
    }

    fun getName(context: SecurityContext): String? {
        return if (context.authentication.principal is AppUser) {
            (context.authentication.principal as AppUser).firstName
        } else null
    }

    @RequestMapping("/search")
    fun searchQuery(
        @CurrentSecurityContext context: SecurityContext,
        @RequestParam(defaultValue = "0.0") lon: Double,
        @RequestParam(defaultValue = "0.0") lat: Double,
        @RequestParam myLon: Optional<Double>,
        @RequestParam myLat: Optional<Double>,
        @RequestParam(name = "distance", defaultValue = "0.01") distanceInMeters: Double,
        @RequestParam(name = "zoom", defaultValue = "7") zoom: Int,
        pageable: Pageable,
        model: Model
    ): String {
        model.addAttribute("pets", petService.findAllByLngLat(lon, lat, distanceInMeters, pageable).content)
        model.addAttribute("lon", lon)
        model.addAttribute("lat", lat)
        model.addAttribute("zoom", zoom)
        model.addAttribute("firstName", getName(context))
        myLat.ifPresent { model.addAttribute("myLat", it) }
        myLon.ifPresent { model.addAttribute("myLon", it) }
        model.addAttribute("init", false)
        return "index"
    }
}