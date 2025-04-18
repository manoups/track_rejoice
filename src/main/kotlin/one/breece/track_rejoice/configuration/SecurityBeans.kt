package one.breece.track_rejoice.configuration

import org.flywaydb.core.Flyway
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository
import javax.sql.DataSource


@Configuration
class SecurityBeans {
    @Bean
    fun persistentTokenRepository(datasource: DataSource?): PersistentTokenRepository {
        val tokenRepositoryImpl = JdbcTokenRepositoryImpl()
        tokenRepositoryImpl.setDataSource(datasource!!)
        return tokenRepositoryImpl
    }
}