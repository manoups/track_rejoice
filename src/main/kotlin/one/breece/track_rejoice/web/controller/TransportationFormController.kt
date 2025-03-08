package one.breece.track_rejoice.web.controller

import jakarta.validation.Valid
import one.breece.track_rejoice.commands.MeansOfTransportationAnnouncementCommand
import one.breece.track_rejoice.service.TransportationService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/apb/form/transport")
class TransportationFormController(private val transportationService: TransportationService) {
    @GetMapping(value = ["","/"])
    fun checkoutForm(model: Model): String {
        model.addAttribute("meansOfTransportationAnnouncementCommand",  MeansOfTransportationAnnouncementCommand())
//        model.addAttribute("dogs", resourceFile)
        model.addAttribute("action", "create")
        return "meansoftransportsearchform"
    }

    @PostMapping(value = ["","/"])
    fun index(@Valid meansOfTransportationAnnouncementCommand: MeansOfTransportationAnnouncementCommand, bindingResult: BindingResult, model: Model): String {
        model.addAttribute("action", "create")
        return if (bindingResult.hasErrors()) {
            "meansoftransportsearchform"
        } else {
            val apb = transportationService.createAPB(meansOfTransportationAnnouncementCommand)
            "redirect:/apb/form/transport/created/${apb.id}"
        }
    }

    @GetMapping("/created/{id}")
    fun created(@PathVariable id: Long, model: Model): String {
        val apb = transportationService.readById(id)
        model.addAttribute("meansOfTransportationAnnouncementCommand", apb)
        model.addAttribute("action", "publish")
        return "meansoftransportsearchform"
    }
}