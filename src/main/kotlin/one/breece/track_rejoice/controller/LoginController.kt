package one.breece.track_rejoice.controller

import one.breece.track_rejoice.commands.LoginCommand
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class LoginController {
    @RequestMapping("/login")
    fun login(@RequestParam(defaultValue = "false") logout:Boolean, model: Model): String {
        model.addAttribute("logout",  logout)
        model.addAttribute("loginCommand",  LoginCommand())
        return "login"
    }
}