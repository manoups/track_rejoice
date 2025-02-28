package one.breece.track_rejoice.web.controller

import one.breece.track_rejoice.service.UtilService
import org.springframework.data.domain.Pageable
import org.springframework.security.core.annotation.CurrentSecurityContext
import org.springframework.security.core.context.SecurityContext
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import java.util.*

@Controller
@RequestMapping("/announcement")
class GeomanController(
    val utilService: UtilService,
) {
    @GetMapping("/create")
    fun index(@CurrentSecurityContext context: SecurityContext,
              @RequestParam(defaultValue = "0.0") lon: Double,
              @RequestParam(defaultValue = "0.0") lat: Double,
              @RequestParam myLon: Optional<Double>,
              @RequestParam myLat: Optional<Double>,
              @RequestParam(name = "distance", defaultValue = "0.01") distanceInMeters: Double,
              @RequestParam(name = "zoom", defaultValue = "7") zoom: Int,
              @RequestParam(defaultValue = "false") identify: Boolean,
              pageable: Pageable,
              model: Model): String {
        model.addAttribute("lon", lon)
        model.addAttribute("lat", lat)
        model.addAttribute("zoom", zoom)
        model.addAttribute("identify", identify)
        model.addAttribute("firstName", utilService.getName(context))
        myLat.ifPresent { model.addAttribute("myLat", it) }
        myLon.ifPresent { model.addAttribute("myLon", it) }
        return "geoman"
    }

/*    @PostMapping("/create/admin")
    fun create(): String {
        val admin = AppUser(passwordEncoder.encode("admin"), "admin", "Manos", "Psanis").also { it.enabled=true }
        val role = Role(name = "ROLE_ADMIN")
        roleRepository.saveAndFlush(role)
        userRepository.save(admin)
        return "ok"
    }

    @PostMapping("/create/user")
    fun createUser(): String {
        val user = AppUser(passwordEncoder.encode("user"), "user", "Manos", "Psanis").also { it.enabled=true }
        val role = "ROLE_USER"
        roleRepository.findByName(role)
        userRepository.save(user)
        return "ok"
    }*/
}