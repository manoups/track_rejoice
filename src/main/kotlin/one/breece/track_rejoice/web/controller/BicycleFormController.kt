package one.breece.track_rejoice.web.controller

import jakarta.validation.Valid
import one.breece.track_rejoice.commands.BicycleAnnouncementCommand
import one.breece.track_rejoice.service.BicycleService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import java.util.UUID

@Controller
@RequestMapping("/bolo/form/transport")
class BicycleFormController(private val transportationService: BicycleService) {
    @GetMapping(value = ["","/"])
    fun checkoutForm(model: Model): String {
        model.addAttribute("bicycleAnnouncementCommand",  BicycleAnnouncementCommand())
//        model.addAttribute("dogs", resourceFile)
        return "bikesearchform"
    }

    @PostMapping(value = ["","/"])
    fun index(@Valid bicycleAnnouncementCommand: BicycleAnnouncementCommand, bindingResult: BindingResult, model: Model): String {
        return if (bindingResult.hasErrors()) {
            "bikesearchform"
        } else {
            val bolo = transportationService.createAPB(bicycleAnnouncementCommand)
            "redirect:/bolo/form/transport/created/${bolo.sku}"
        }
    }

    @GetMapping("/created/{sku}")
    fun created(@PathVariable sku: UUID, model: Model): String {
        val bolo = transportationService.readBySku(sku)
        model.addAttribute("bicycleAnnouncementCommand", bolo)
        return "bikesearchformaccepted"
    }
}