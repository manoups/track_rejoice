package one.breece.track_rejoice.controller

import jakarta.servlet.http.HttpServletRequest
import one.breece.track_rejoice.commands.LoginCommand
import one.breece.track_rejoice.commands.UserCommand
import org.springframework.context.MessageSource
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import java.util.*


@Controller
class LoginController(
    var messages: MessageSource
) {

    @RequestMapping("/login")
    fun login(
        request: HttpServletRequest,
        model: Model,
        @RequestParam("messageKey") messageKey: Optional<String>,
        @RequestParam("error") error: Optional<String>
    ): String {
        val locale: Locale = request.locale
        model.addAttribute("lang", locale.language)
        messageKey.ifPresent {
            model.addAttribute("message", messages.getMessage(it, null, locale))
        }
        error.ifPresent { model.addAttribute("error", it) }
        model.addAttribute("loginCommand", LoginCommand())
        return "login"
    }

    @GetMapping("/register-v3")
    fun showRegistrationFormV3(model: Model): String {
        // create model object to store form data
        model.addAttribute("user", UserCommand())
        return "register-v3"
    }




}