package one.breece.track_rejoice.configuration.query

import one.breece.track_rejoice.domain.query.BeOnTheLookOut
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.sql.DataSource


@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    basePackageClasses = [BeOnTheLookOut::class],
    basePackages = ["one.breece.track_rejoice.repository.query"],
    entityManagerFactoryRef = "queryEntityManagerFactory",
    transactionManagerRef = "queryTransactionManager"
)
class QueryJpaConfiguration {
    @Bean
    fun queryEntityManagerFactory(
        @Qualifier("queryDataSource") dataSource: DataSource?,
        builder: EntityManagerFactoryBuilder
    ): LocalContainerEntityManagerFactoryBean {
        return builder
            .dataSource(dataSource)
            .packages(BeOnTheLookOut::class.java)
            .build()
    }

    @Bean
    fun queryTransactionManager(
        @Qualifier("queryEntityManagerFactory") queryEntityManagerFactory: LocalContainerEntityManagerFactoryBean
    ): PlatformTransactionManager {
        return JpaTransactionManager(queryEntityManagerFactory.getObject()!!)
    }
}