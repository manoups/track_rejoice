package one.breece.track_rejoice.web.controller

import one.breece.track_rejoice.service.BoloService
import one.breece.track_rejoice.service.UtilService
import org.springframework.data.domain.Pageable
import org.springframework.security.core.annotation.CurrentSecurityContext
import org.springframework.security.core.context.SecurityContext
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import java.util.*


@Controller
class SearchController(
    private val boloService: BoloService,
    private val utilService: UtilService,
) {

    /**
     * We need two sets of coordinates here:
     * Map center coordinates:mandatory, used in the DB query and re-center the map
     * User coordinates: optional, used to initialize the map if user
     * has enabled geolocation on the browser/app
     * */
    @RequestMapping("/search")
    fun searchQuery(
        @CurrentSecurityContext context: SecurityContext,
        @RequestParam(defaultValue = "0.0") lng: Double,
        @RequestParam(defaultValue = "0.0") lat: Double,
        @RequestParam myLng: Optional<Double>,
        @RequestParam myLat: Optional<Double>,
        @RequestParam(name = "distance", defaultValue = "0.01") distanceInMeters: Double,
        @RequestParam(name = "zoom", defaultValue = "7") zoom: Int,
        @RequestParam(defaultValue = "false") identify: Boolean,
        pageable: Pageable,
        model: Model
    ): String {
        val content = boloService.findAllByLngLat(lng, lat, 0.1 / (zoom/1.5), pageable).content
        model.addAttribute("pets", content)
        model.addAttribute("lng", lng)
        model.addAttribute("lat", lat)
        model.addAttribute("zoom", zoom)
        model.addAttribute("identify", identify)
        model.addAttribute("firstName", utilService.getName(context))
        myLat.ifPresent { model.addAttribute("myLat", it) }
        myLng.ifPresent { model.addAttribute("myLng", it) }
        return "index"
    }

}