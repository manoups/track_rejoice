package one.breece.track_rejoice.web.controller

import jakarta.validation.Valid
import one.breece.track_rejoice.commands.APBCommand
import one.breece.track_rejoice.service.PetService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/apb")
class APBFormController(private val petService: PetService) {

    @GetMapping("/form")
    fun checkoutForm(model: Model): String {
        model.addAttribute("apbCommand",  APBCommand())
        return "checkoutform"
    }

    @PostMapping("/form")
    fun index(@Valid apbCommand: APBCommand, bindingResult: BindingResult): String {
        return if (bindingResult.hasErrors()) {
            "checkoutform"
        } else {
            petService.createAPB(apbCommand)
            "checkoutcomplete"
        }
    }
}