package one.breece.track_rejoice.configuration

import org.n52.jackson.datatype.jts.JtsModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class GeometryConfig {
    @Bean
    fun jtsModule(): JtsModule {
        return JtsModule()
    }

}