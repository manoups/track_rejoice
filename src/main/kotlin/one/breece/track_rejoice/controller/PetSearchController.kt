package one.breece.track_rejoice.controller

import jakarta.validation.Valid
import one.breece.track_rejoice.commands.PetSearchCommand
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
//@RequestMapping("/announcement")
class PetSearchController {

    @RequestMapping("/searchform")
    fun checkoutForm(model: Model): String {
        model.addAttribute("petSearchCommand",  PetSearchCommand())
        return "checkoutform"
    }

    @PostMapping("/post")
    fun index(@Valid petSearchCommand: PetSearchCommand, bindingResult: BindingResult): String {
        return if (bindingResult.hasErrors()) {
            "checkoutform"
        } else {
            "checkoutcomplete"
        }
    }
}