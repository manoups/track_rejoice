package one.breece.track_rejoice.web.controller

import one.breece.track_rejoice.domain.command.AppUser
import one.breece.track_rejoice.repository.command.UserRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/account")
class AccountController(private val userRepository: UserRepository) {
    val LOGGER: Logger = LoggerFactory.getLogger(javaClass)

    @GetMapping(value = ["", "/"])
    fun account(model: Model): String {
        return "account-management"
    }

    @DeleteMapping("/delete")
    fun delete(@AuthenticationPrincipal user: AppUser): String {
        user.accountNonLocked = false
        userRepository.save(user)
        return "redirect:/logout"
    }
}