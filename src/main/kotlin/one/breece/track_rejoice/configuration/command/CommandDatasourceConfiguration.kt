package one.breece.track_rejoice.configuration.command

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.jdbc.core.JdbcTemplate
import javax.sql.DataSource


@Configuration
class CommandDatasourceConfiguration {
    @Bean
    @ConfigurationProperties("spring.datasource.command")
    fun commandDataSourceProperties(): DataSourceProperties {
        return DataSourceProperties()
    }

    @Primary
    @Bean
    @ConfigurationProperties("spring.datasource.hikari")
    fun commandDataSource(): DataSource {
        return commandDataSourceProperties()
            .initializeDataSourceBuilder()
            .build()
    }

    @Bean
    fun commandJdbcTemplate(@Qualifier("commandDataSource") dataSource: DataSource?): JdbcTemplate {
        return JdbcTemplate(dataSource!!)
    }
}