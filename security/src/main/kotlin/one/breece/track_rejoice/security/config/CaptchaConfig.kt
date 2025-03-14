package one.breece.track_rejoice.security.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.ClientHttpRequestFactory
import org.springframework.http.client.SimpleClientHttpRequestFactory
import org.springframework.web.client.RestOperations
import org.springframework.web.client.RestTemplate

@Configuration
@ComponentScan(basePackages = ["one.breece.track_rejoice"])
class CaptchaConfig {
    @Bean
    fun clientHttpRequestFactory(): ClientHttpRequestFactory {
        val factory = SimpleClientHttpRequestFactory()
        factory.setConnectTimeout(3 * 1000)
        factory.setReadTimeout(7 * 1000)
        return factory
    }

    @Bean
    fun restTemplate(): RestOperations {
        val restTemplate = RestTemplate(this.clientHttpRequestFactory())
        return restTemplate
    }
}