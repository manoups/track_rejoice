package one.breece.track_rejoice.web.controller

import jakarta.validation.Valid
import one.breece.track_rejoice.commands.PetAnnouncementCommand
import one.breece.track_rejoice.service.PetService
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import java.util.UUID

@Controller
@RequestMapping("/bolo/form/pet")
class PetFormController(private val petService: PetService) {

//    @Value("classpath:static/text/dogs.txt")
//    lateinit var resourceFile: Resource

    @GetMapping(value = ["","/"])
    fun checkoutForm(model: Model): String {
        model.addAttribute("petAnnouncementCommand",  PetAnnouncementCommand())
//        model.addAttribute("dogs", resourceFile)
//        model.addAttribute("action", "create")
        return "petsearchform"
    }

    @PostMapping(value = ["","/"])
    fun index(@Valid petAnnouncementCommand: PetAnnouncementCommand, bindingResult: BindingResult, model: Model): String {
        model.addAttribute("action", "create")
        return if (bindingResult.hasErrors()) {
            "petsearchform"
        } else {
            val bolo = petService.createAPB(petAnnouncementCommand)
            "redirect:/bolo/form/pet/created/${bolo.sku}"
        }
    }

    @GetMapping("/created/{sku}")
    fun created(@PathVariable sku: UUID, model: Model): String {
        val bolo = petService.readBySku(sku)
        model.addAttribute("petAnnouncementCommand", bolo)
        return "petsearchformaccepted"
    }
}