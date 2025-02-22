package one.breece.track_rejoice.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.LocaleResolver
import org.springframework.web.servlet.config.annotation.*
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor
import org.springframework.web.servlet.i18n.SessionLocaleResolver
import java.util.*


@Configuration
//@ComponentScan(basePackages = ["one.breece.track_rejoice"])
//@EnableWebMvc
class SpringMvcConfiguration : WebMvcConfigurer {
    @Bean
    fun localeResolver(): LocaleResolver {
        val sessionLocaleResolver = SessionLocaleResolver()
        sessionLocaleResolver.setDefaultLocale(Locale.US)
        return sessionLocaleResolver
    }

    @Bean
    fun localeChangeInterceptor(): LocaleChangeInterceptor {
        val localeChangeInterceptor = LocaleChangeInterceptor()
        localeChangeInterceptor.paramName = "lang"
        return localeChangeInterceptor
    }

//    override fun addViewControllers(registry: ViewControllerRegistry) {
//        registry.addViewController("/register-v3.html")
//        registry.addViewController("/successRegister.html")
//        registry.addViewController("/emailError.html")
//    }

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(localeChangeInterceptor())
    }

//    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
//        registry.addResourceHandler("/resources/**").addResourceLocations("/", "/resources/", "/resources/static/")
//    }
}