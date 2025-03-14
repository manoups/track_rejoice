package one.breece.track_rejoice.query.web.controller

import one.breece.track_rejoice.query.service.PetQueryService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import java.util.*

@Controller
@RequestMapping("/details/pet")
class PetDetailsViewController(private val service: PetQueryService) {
    @GetMapping("/{sku}")
    fun itemDetailsView(model: Model, @PathVariable sku: UUID): String {
        val petResponseCommand = service.readBySku(sku)
        model.addAttribute("petAnnouncementCommand", petResponseCommand)
        model.addAttribute("action", "publish")
        model.addAttribute("photos", petResponseCommand.photos)
        return "petsearchformaccepted"
    }
}