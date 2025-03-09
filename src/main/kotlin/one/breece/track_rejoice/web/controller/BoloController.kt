package one.breece.track_rejoice.web.controller

import jakarta.servlet.http.HttpServletRequest
import one.breece.track_rejoice.domain.AppUser
import one.breece.track_rejoice.domain.Role
import one.breece.track_rejoice.repository.BeOnTheLookOutRepository
import org.springframework.http.HttpRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.annotation.CurrentSecurityContext
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.context.SecurityContextImpl
import org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/bolo")
class BoloController(val boloRepository: BeOnTheLookOutRepository) {

/*    @PostMapping
    fun createUUIDs(@CurrentSecurityContext context: SecurityContext, request: HttpServletRequest): ResponseEntity<String> {
        SecurityContextHolder.setContext(
            SecurityContextImpl(
                UsernamePasswordAuthenticationToken(AppUser("", "emmanouil.psanis@gmail.com", "Jack", "Psanis", 1), null, setOf(Role(name = "ROLE_")))))
        request.getSession(true).setAttribute(SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext())
        val map = boloRepository.findAll().map { it.sku = UUID.randomUUID(); return@map it }
        boloRepository.saveAll(map)
        return ResponseEntity<String>("OK", HttpStatus.OK)
    }*/
}