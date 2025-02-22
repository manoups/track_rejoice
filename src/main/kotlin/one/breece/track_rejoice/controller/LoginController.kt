package one.breece.track_rejoice.controller

import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import one.breece.track_rejoice.commands.LoginCommand
import one.breece.track_rejoice.commands.UserCommand
import one.breece.track_rejoice.security.captcha.CaptchaServiceV3
import one.breece.track_rejoice.service.UserService
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.MessageSource
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import java.util.*


@Controller
class LoginController(
    var messages: MessageSource,
    private val userService: UserService,
    val captchaServiceV3: CaptchaServiceV3,
    var eventPublisher: ApplicationEventPublisher
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

    // handler method to handle user registration form request
    /*@GetMapping("/register")
    fun showRegistrationForm(model: Model): String {
        // create model object to store form data
        model.addAttribute("user", UserCommand())
        return "register"
    }

    // handler method to handle user registration form submit request
    @PostMapping("/register/save")
    fun registration(
        @Valid @ModelAttribute("user") userCommand: UserCommand,
        result: BindingResult,
        model: Model
    ): String {
        if (result.hasErrors()) {
            model.addAttribute("user", userCommand)
            return "/register"
        }
        val existingUser = userService.findUserByEmail(userCommand.email!!)

        if (!existingUser.isEmpty) {
            result.rejectValue(
                "email", "500",
                "There is already an account registered with the same email"
            )
        }

        if (result.hasErrors()) {
            model.addAttribute("user", userCommand)
            return "/register"
        }

        userService.saveUser(userCommand)
        return "redirect:/register?success"
    }
*/
    @GetMapping("/register-v3")
    fun showRegistrationFormV3(model: Model): String {
        // create model object to store form data
        model.addAttribute("user", UserCommand())
        return "register-v3"
    }




}