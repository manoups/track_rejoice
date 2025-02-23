package one.breece.track_rejoice.configuration

import one.breece.track_rejoice.domain.AppUser
import org.hibernate.cfg.AvailableSettings
import org.hibernate.context.spi.CurrentTenantIdentifierResolver
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer
import org.springframework.context.annotation.Configuration
import org.springframework.security.core.context.SecurityContextHolder

@Configuration
class TenantIdentifierResolver: CurrentTenantIdentifierResolver<Long>, HibernatePropertiesCustomizer {
    override fun resolveCurrentTenantIdentifier(): Long {
        val authentication = SecurityContextHolder.getContext().authentication
        return if (authentication == null || authentication.principal !is AppUser) {
            -1
        } else
            (SecurityContextHolder.getContext().authentication.principal as AppUser).id!!
    }

    override fun validateExistingCurrentSessions() = false

    override fun isRoot(tenantId: Long): Boolean {
        val authentication = SecurityContextHolder.getContext().authentication
        return if (authentication == null || authentication.principal !is AppUser) {
            false
        } else
            (SecurityContextHolder.getContext().authentication.principal as AppUser).authorities.map { it.authority }.contains("ROLE_ADMIN")
    }

    override fun customize(hibernateProperties: MutableMap<String, Any>) {
        hibernateProperties[AvailableSettings.MULTI_TENANT_IDENTIFIER_RESOLVER] = this
    }
}