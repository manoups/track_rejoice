package one.breece.track_rejoice.web.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class PublishController {
    @GetMapping("/checkout/success")
    fun checkoutSuccess(): String {
        return "checkoutsuccess"
    }
}