package one.breece.track_rejoice.configuration.query

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.JdbcTemplate
import javax.sql.DataSource


@Configuration
class QueryDatasourceConfiguration {
    @Bean
    @ConfigurationProperties("spring.datasource.query")
    fun queryDataSourceProperties(): DataSourceProperties {
        return DataSourceProperties()
    }

    @Bean
    @ConfigurationProperties("spring.datasource.hikari")
    fun queryDataSource(): DataSource {
        return queryDataSourceProperties()
            .initializeDataSourceBuilder()
            .build()
    }

    @Bean
    fun commandJdbcTemplate(@Qualifier("queryDataSource") dataSource: DataSource?): JdbcTemplate {
        return JdbcTemplate(dataSource!!)
    }
}