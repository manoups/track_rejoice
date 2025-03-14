package one.breece.track_rejoice.command.web.controller

import jakarta.validation.Valid
import one.breece.track_rejoice.command.command.ItemAnnouncementCommand
import one.breece.track_rejoice.command.service.ItemService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import java.util.*

@Controller
@RequestMapping("/bolo/form/item")
class ItemSearchController(private val itemService: ItemService) {
    @GetMapping(value = ["", "/"])
    fun checkoutForm(model: Model): String {
        model.addAttribute("itemAnnouncementCommand", ItemAnnouncementCommand())
//        model.addAttribute("dogs", resourceFile)
        model.addAttribute("action", "create")
        return "itemsearchform"
    }

    @PostMapping(value = ["", "/"])
    fun index(
        @Valid itemAnnouncementCommand: ItemAnnouncementCommand,
        bindingResult: BindingResult,
        model: Model
    ): String {
        model.addAttribute("action", "create")
        return if (bindingResult.hasErrors()) {
            "itemsearchform"
        } else {
            val bolo = itemService.createBolo(itemAnnouncementCommand)
            "redirect:/bolo/form/item/created/${bolo.sku}"
        }
    }

    @GetMapping("/created/{sku}")
    fun created(@PathVariable sku: UUID, model: Model): String {
        val itemResponseCommand = itemService.readBySku(sku)
        model.addAttribute("itemAnnouncementCommand", itemResponseCommand)
        model.addAttribute("action", "publish")
        model.addAttribute("photos", itemResponseCommand.photos)
        return "itemsearchformaccepted"
    }
}