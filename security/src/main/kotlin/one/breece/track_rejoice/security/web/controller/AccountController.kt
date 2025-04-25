package one.breece.track_rejoice.security.web.controller

import one.breece.track_rejoice.security.domain.AppUser
import one.breece.track_rejoice.security.repository.UserRepository
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@PreAuthorize("isFullyAuthenticated()")
@RequestMapping("/account")
class AccountController(private val userRepository: UserRepository) {

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