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
class PetController(
    private val boloService: BoloService,
    private val utilService: UtilService,
    val utilServicegetName: UtilService
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
        @RequestParam(defaultValue = "0.0") lon: Double,
        @RequestParam(defaultValue = "0.0") lat: Double,
        @RequestParam myLon: Optional<Double>,
        @RequestParam myLat: Optional<Double>,
        @RequestParam(name = "distance", defaultValue = "0.01") distanceInMeters: Double,
        @RequestParam(name = "zoom", defaultValue = "7") zoom: Int,
        @RequestParam(defaultValue = "false") identify: Boolean,
        pageable: Pageable,
        model: Model
    ): String {
        val content = boloService.findAllByLngLat(lon, lat, 0.1 / (zoom/2.0), pageable).content
        model.addAttribute("pets", content)
        model.addAttribute("lon", lon)
        model.addAttribute("lat", lat)
        model.addAttribute("zoom", zoom)
        model.addAttribute("identify", identify)
        model.addAttribute("firstName", utilServicegetName.getName(context))
        myLat.ifPresent { model.addAttribute("myLat", it) }
        myLon.ifPresent { model.addAttribute("myLon", it) }
        return "index"
    }

}