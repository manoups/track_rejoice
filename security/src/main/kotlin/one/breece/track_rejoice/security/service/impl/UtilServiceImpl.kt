package one.breece.track_rejoice.security.service.impl

import one.breece.track_rejoice.security.domain.AppUser
import one.breece.track_rejoice.security.service.UtilService
import org.springframework.security.core.context.SecurityContext
import org.springframework.stereotype.Service

@Service
//@PreAuthorize("hasRole('ROLE_ADMIN')")
class UtilServiceImpl : UtilService {

    override fun getName(context: SecurityContext): String? {
        return if (context.authentication.principal is AppUser) {
            (context.authentication.principal as AppUser).firstName
        } else null
    }

    override fun getUsername(context: SecurityContext): String? {
        return if (context.authentication.principal is AppUser) {
            (context.authentication.principal as AppUser).username
        } else null
    }
}