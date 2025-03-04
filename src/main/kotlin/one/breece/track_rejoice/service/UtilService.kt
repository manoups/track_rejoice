package one.breece.track_rejoice.service

import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContext

interface UtilService {

    fun addHumanReadableAddress(id: Long): ResponseEntity<String>
    fun getName(context: SecurityContext): String?
    fun getUsername(context: SecurityContext): String?
}
