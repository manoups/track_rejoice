package one.breece.track_rejoice.controller

import jakarta.validation.Valid
import one.breece.track_rejoice.commands.APBCommand
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/apb")
class PetSearchController {

    @GetMapping("/form")
    fun checkoutForm(model: Model): String {
        model.addAttribute("petSearchCommand",  APBCommand())
        return "checkoutform"
    }

    @PostMapping("/form")
    fun index(@Valid APBCommand: APBCommand, bindingResult: BindingResult): String {
        return if (bindingResult.hasErrors()) {
            "checkoutform"
        } else {
            "checkoutcomplete"
        }
    }
}