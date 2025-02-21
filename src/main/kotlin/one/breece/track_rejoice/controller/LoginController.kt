package one.breece.track_rejoice.controller

import jakarta.validation.Valid
import one.breece.track_rejoice.commands.LoginCommand
import one.breece.track_rejoice.commands.UserCommand
import one.breece.track_rejoice.service.UserService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*


@Controller
class LoginController(private val userService: UserService) {
    @RequestMapping("/login")
    fun login(model: Model): String {
        model.addAttribute("loginCommand", LoginCommand())
        return "login"
    }

    // handler method to handle user registration form request
    @GetMapping("/register")
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
}