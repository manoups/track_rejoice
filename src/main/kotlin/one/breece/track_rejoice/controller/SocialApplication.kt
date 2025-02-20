package one.breece.track_rejoice.controller

import org.springframework.boot.SpringApplication
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


/*
@RestController
class SocialApplication: WebSecurityConfiguration() {
    @GetMapping("/user")
    fun user(@AuthenticationPrincipal principal: OAuth2User): Map<String, Any?> {
        return mapOf(pair = Pair("name", principal.getAttribute("name")))
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(SocialApplication::class.java, *args)
        }
    }
}*/
