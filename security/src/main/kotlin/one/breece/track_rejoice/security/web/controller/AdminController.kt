package one.breece.track_rejoice.security.web.controller

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.CurrentSecurityContext
import org.springframework.security.core.context.SecurityContext
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/admin")
class AdminController {

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping(value = ["", "/"])
    fun admin(@CurrentSecurityContext context: SecurityContext?): String {
        return "admin-management"
    }
}