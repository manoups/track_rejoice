package one.breece.track_rejoice.configuration

import one.breece.track_rejoice.security.command.UserCommand
import one.breece.track_rejoice.security.domain.Provider
import one.breece.track_rejoice.security.service.impl.UserServiceImpl
import org.springframework.boot.autoconfigure.security.servlet.RequestMatcherProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.access.expression.SecurityExpressionHandler
import org.springframework.security.access.hierarchicalroles.RoleHierarchy
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.session.SessionRegistry
import org.springframework.security.core.session.SessionRegistryImpl
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.security.web.FilterInvocation
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository
import org.springframework.security.web.session.HttpSessionEventPublisher
import org.springframework.security.web.util.matcher.AntPathRequestMatcher


@Configuration
@EnableWebSecurity
class SecurityConfig(val customTokenRepository: PersistentTokenRepository) {
    @Bean
    fun filterChain(
        http: HttpSecurity,
        customAuthenticationFailureHandler: AuthenticationFailureHandler,
        requestMatcherProvider: RequestMatcherProvider
    ): SecurityFilterChain {
        http {
            headers { frameOptions { sameOrigin = true } }
            csrf { ignoringRequestMatchers("/api/orders/**") }
//            csrf { disable() }
            authorizeHttpRequests {
                authorize("/public/**", permitAll)
                authorize("/login/**", permitAll)
                authorize("/css/**", permitAll)
                authorize("/js/**", permitAll)
                authorize("/user/**", hasAuthority("ROLE_USER"))
                authorize("/login*", permitAll)
                authorize("/password-*", permitAll)
                authorize("/actuator*", permitAll)
                authorize("/actuator/**", permitAll)
                authorize("/register*", permitAll)
                authorize("/register/**", permitAll)
                authorize("/successRegister*", permitAll)
                authorize("/", permitAll)
                authorize("/details/**", permitAll)
                authorize("/process*", permitAll)
                authorize("/search*", permitAll)
                authorize("/emailError*", permitAll)
                authorize("/successRegister*", permitAll)
                authorize("/api/v1/util/**", hasAuthority("ROLE_ADMIN"))
                authorize(anyRequest, authenticated)
            }
            formLogin {
                loginPage = "/login"
                failureUrl = "/login?error=true"
                defaultSuccessUrl("/", true)
//                permitAll = true
                authenticationFailureHandler = customAuthenticationFailureHandler
            }
            oauth2Login {
                loginPage = "/login"
                defaultSuccessUrl("/", true)
            }
            logout {
                logoutRequestMatcher = AntPathRequestMatcher("/logout", "GET")
                clearAuthentication = true
                invalidateHttpSession = true
                logoutSuccessUrl = "/"
                deleteCookies("remove", "remember-me")
            }
            rememberMe { tokenRepository = customTokenRepository }
        }
        return http.build()
    }

    @Bean
    fun customWebSecurityExpressionHandler(): SecurityExpressionHandler<FilterInvocation> {
        val expressionHandler = DefaultWebSecurityExpressionHandler()
        expressionHandler.setRoleHierarchy(roleHierarchy())
        return expressionHandler
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder(11)
    }

    @Bean
    fun sessionRegistry(): SessionRegistry {
        return SessionRegistryImpl()
    }

    @Bean
    fun roleHierarchy(): RoleHierarchy {
        val hierarchy = "ROLE_ADMIN > ROLE_STAFF \n ROLE_STAFF > ROLE_USER"
        return RoleHierarchyImpl.fromHierarchy(hierarchy)
    }

    @Bean
    fun httpSessionEventPublisher(): HttpSessionEventPublisher {
        return HttpSessionEventPublisher()
    }

    @Bean
    fun oauth2UserService(userDetailsService: UserServiceImpl?): OAuth2UserService<OAuth2UserRequest, OAuth2User> {
        return OAuth2UserService {
            val delegate = DefaultOAuth2UserService()
            val oauth2User = delegate.loadUser(it)

            val email = oauth2User.attributes["email"] as String
            val firstName = oauth2User.attributes["given_name"] as String
            val last = oauth2User.attributes["family_name"] as String

            if (!userDetailsService!!.userExists(email)) {
                userDetailsService.saveUser(UserCommand(firstName, last, "{noop}", email), Provider.GOOGLE)
            }
            val userDetails = userDetailsService.findByEmail(email).get()
            SecurityContextHolder.getContext().authentication =
                org.springframework.security.authentication.UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities()
                )

            userDetails
        }
    }
}