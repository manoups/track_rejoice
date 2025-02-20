package one.breece.track_rejoice.controller

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class IndexController {
    @RequestMapping("secured")
    fun secured(): String {
        val context = SecurityContextHolder.getContext().authentication.isAuthenticated
        return "secured"
    }
}