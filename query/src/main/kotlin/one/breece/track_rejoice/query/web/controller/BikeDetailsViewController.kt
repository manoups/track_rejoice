package one.breece.track_rejoice.query.web.controller

import one.breece.track_rejoice.query.service.BikeQueryService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import java.util.*

@Controller
@RequestMapping("/details/bike")
class BikeDetailsViewController(private val bikeQueryService: BikeQueryService) {
    @GetMapping("/{sku}")
    fun itemDetailsView(model: Model, @PathVariable sku: UUID): String {
        val bicycleResponseCommand = bikeQueryService.readBySku(sku)
        model.addAttribute("bicycleAnnouncementCommand", bicycleResponseCommand)
        model.addAttribute("action", "publish")
        model.addAttribute("photos", bicycleResponseCommand.photos)
        return "bikesearchformaccepted"
    }
}