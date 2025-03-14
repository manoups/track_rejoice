package one.breece.track_rejoice.configuration.command

import one.breece.track_rejoice.domain.command.BeOnTheLookOut
import one.breece.track_rejoice.security.domain.AppUser
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.sql.DataSource

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    basePackages = ["one.breece.track_rejoice.repository.command"],
    basePackageClasses = [BeOnTheLookOut::class, AppUser::class],
    entityManagerFactoryRef = "commandEntityManagerFactory",
    transactionManagerRef = "commandTransactionManager"
)
class CommandJpaConfiguration {
    @Primary
    @Bean
    fun commandEntityManagerFactory(
        @Qualifier("commandDataSource") dataSource: DataSource?,
        builder: EntityManagerFactoryBuilder
    ): LocalContainerEntityManagerFactoryBean {
        return builder
            .dataSource(dataSource)
            .packages(BeOnTheLookOut::class.java, AppUser::class.java)
            .build()
    }

    @Primary
    @Bean
    fun commandTransactionManager(
        @Qualifier("commandEntityManagerFactory") commandEntityManagerFactory: LocalContainerEntityManagerFactoryBean
    ): PlatformTransactionManager {
        return JpaTransactionManager(commandEntityManagerFactory.getObject()!!)
    }
}