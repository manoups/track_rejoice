package one.breece.track_rejoice.security.service

import org.springframework.security.core.context.SecurityContext

interface UtilService {

    fun getName(context: SecurityContext): String?
    fun getUsername(context: SecurityContext): String?
}
