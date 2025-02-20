package one.breece.track_rejoice.controller

import jakarta.validation.Valid
import one.breece.track_rejoice.commands.LoginCommand
import one.breece.track_rejoice.service.PetService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
//@RequestMapping("/login")
class LoginController(private val petService: PetService) {
    @RequestMapping("/login")
    fun checkoutForm(model: Model): String {
        model.addAttribute("loginCommand",  LoginCommand())
        return "login"
    }

    @PostMapping("/dologin")
    fun index(@Valid loginCommand: LoginCommand, bindingResult: BindingResult, model: Model): String {
        return if (bindingResult.hasErrors()) {
            "login"
        } else {
            model.addAttribute("pets", petService.findAll())
            return "redirect:/pets"
        }
    }
}