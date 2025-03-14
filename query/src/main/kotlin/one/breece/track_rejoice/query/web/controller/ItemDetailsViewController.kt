package one.breece.track_rejoice.query.web.controller

import one.breece.track_rejoice.query.service.ItemQueryService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import java.util.UUID

@Controller
@RequestMapping("/details/item")
class ItemDetailsViewController(private val service: ItemQueryService) {
    @GetMapping("/{sku}")
    fun itemDetailsView(model: Model, @PathVariable sku: UUID): String {
        val itemResponseCommand = service.readBySku(sku)
        model.addAttribute("itemAnnouncementCommand", itemResponseCommand)
        model.addAttribute("action", "publish")
        model.addAttribute("photos", itemResponseCommand.photos)
        return "itemsearchformaccepted"
    }
}