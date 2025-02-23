package one.breece.track_rejoice.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect
import org.thymeleaf.spring6.ISpringTemplateEngine
import org.thymeleaf.spring6.SpringTemplateEngine
import org.thymeleaf.templateresolver.ITemplateResolver

@Configuration
class TemplateEngineConfig {

    @Bean
     fun templateEngine(templateResolver: ITemplateResolver): ISpringTemplateEngine {
        val engine = SpringTemplateEngine()
        engine.addDialect(Java8TimeDialect())
        engine.setTemplateResolver(templateResolver)
        return engine
    }
}