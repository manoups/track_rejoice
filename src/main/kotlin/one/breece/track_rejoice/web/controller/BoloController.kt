package one.breece.track_rejoice.web.controller

import one.breece.track_rejoice.repository.command.BeOnTheLookOutRepository
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

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