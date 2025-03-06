package one.breece.track_rejoice.web.controller

import jakarta.validation.Valid
import one.breece.track_rejoice.commands.ItemAnnouncementCommand
import one.breece.track_rejoice.service.ItemService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/apb/form/item")
class ItemSearchController(private val itemService: ItemService) {
    @GetMapping(value = ["","/"])
    fun checkoutForm(model: Model): String {
        model.addAttribute("itemAnnouncementCommand",  ItemAnnouncementCommand())
//        model.addAttribute("dogs", resourceFile)
        model.addAttribute("action", "create")
        return "itemsearchform"
    }

    @PostMapping(value = ["","/"])
    fun index(@Valid itemAnnouncementCommand: ItemAnnouncementCommand, bindingResult: BindingResult, model: Model): String {
        model.addAttribute("action", "create")
        return if (bindingResult.hasErrors()) {
            "itemsearchform"
        } else {
            val apb = itemService.createAPB(itemAnnouncementCommand)
            "redirect:/apb/form/item/created/${apb.id}"
        }
    }

    @GetMapping("/created/{id}")
    fun created(@PathVariable id: Long, model: Model): String {
        val apb = itemService.readById(id)
        model.addAttribute("itemAnnouncementCommand", apb)
        model.addAttribute("action", "publish")
        return "itemsearchform"
    }
}