package one.breece.track_rejoice.web.controller

import jakarta.validation.Valid
import one.breece.track_rejoice.commands.APBCommand
import one.breece.track_rejoice.service.PetService
import org.springframework.data.jpa.domain.AbstractPersistable_.id
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/apb/form")
class APBFormController(private val petService: PetService) {

    @GetMapping(value = ["","/"])
    fun checkoutForm(model: Model): String {
        model.addAttribute("apbCommand",  APBCommand())
        return "checkoutform"
    }

    @PostMapping(value = ["","/"])
    fun index(@Valid apbCommand: APBCommand, bindingResult: BindingResult, model: Model): String {
        return if (bindingResult.hasErrors()) {
            "checkoutform"
        } else {
            val apb = petService.createAPB(apbCommand)
            "redirect:/apb/form/created/${apb.id}"
        }
    }

    @GetMapping("/created/{id}")
    fun created(@PathVariable id: Long, model: Model): String {
        val apb = petService.readById(id)
        model.addAttribute("apbCommand", apb)
        return "checkoutcomplete"
    }
}