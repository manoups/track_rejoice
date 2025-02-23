package one.breece.track_rejoice.configuration

import one.breece.track_rejoice.repository.UserRepository
import one.breece.track_rejoice.security.CustomRememberMeServices
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.access.expression.SecurityExpressionHandler
import org.springframework.security.access.hierarchicalroles.RoleHierarchy
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.core.session.SessionRegistry
import org.springframework.security.core.session.SessionRegistryImpl
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.FilterInvocation
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.security.web.authentication.RememberMeServices
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl
import org.springframework.security.web.session.HttpSessionEventPublisher


@Configuration
@EnableWebSecurity
class SecurityConfig {
    @Bean
    fun filterChain(
        http: HttpSecurity,
        customAuthenticationFailureHandler: AuthenticationFailureHandler
    ): SecurityFilterChain {
        http {
            csrf {  disable() }
            authorizeHttpRequests {
                authorize("/", authenticated)
                authorize("/apb/*", authenticated)
                authorize("/search", authenticated)
//                authorize("/css/**", permitAll)
//                authorize("/js/**", permitAll)
//                authorize("/user/**", hasAuthority("ROLE_USER"))
//                authorize("login*" , permitAll)
//                authorize("/register*", permitAll)
//                authorize("/register/**", permitAll)
//                authorize("/successRegister*", permitAll)
//                authorize("/emailError*", permitAll)
//                authorize("secured*", permitAll)
                authorize(anyRequest, permitAll)
            }
            formLogin {
                loginPage = "/login"
                failureUrl = "/login?error=true"
                defaultSuccessUrl("/", true)
                authenticationFailureHandler = customAuthenticationFailureHandler
            }
            logout {
                clearAuthentication = true
                invalidateHttpSession = true
                logoutSuccessUrl = "/login?logout=true"
                deleteCookies("remove")
            }
        }
        return http.build()
    }

    @Bean
    fun customWebSecurityExpressionHandler(): SecurityExpressionHandler<FilterInvocation> {
        val expressionHandler = DefaultWebSecurityExpressionHandler()
        expressionHandler.setRoleHierarchy(roleHierarchy())
        return expressionHandler
    }

       /* @Bean
        fun authProvider(userRepository: UserRepository, userDetailsService: UserDetailsService): DaoAuthenticationProvider {
            val authProvider = CustomAuthenticationProvider(userRepository)
            authProvider.setUserDetailsService(userDetailsService)
            authProvider.setPasswordEncoder(passwordEncoder())
    //        authProvider.setPostAuthenticationChecks(differentLocationChecker())
            return authProvider
        }*/

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder(11)
    }

    @Bean
    fun sessionRegistry(): SessionRegistry {
        return SessionRegistryImpl()
    }

    @Bean
    fun rememberMeServices(userDetailsService: UserDetailsService, userRepository: UserRepository): RememberMeServices {
        val rememberMeServices =
            CustomRememberMeServices("theKey", userDetailsService, InMemoryTokenRepositoryImpl(), userRepository)
        return rememberMeServices
    }

/*    @Bean(name = ["GeoIPCountry"])
    @Throws(IOException::class)
    fun databaseReader(): DatabaseReader {
        val resource = File(
            javaClass
                .classLoader
                .getResource("maxmind/GeoLite2-Country.mmdb")
                .file
        )
        return DatabaseReader.Builder(resource).build()
    }*/

    @Bean
    fun roleHierarchy(): RoleHierarchy {
        val roleHierarchy = RoleHierarchyImpl()
        val hierarchy = "ROLE_ADMIN > ROLE_STAFF \n ROLE_STAFF > ROLE_USER"
        roleHierarchy.setHierarchy(hierarchy)
        return roleHierarchy
    }

    @Bean
    fun httpSessionEventPublisher(): HttpSessionEventPublisher {
        return HttpSessionEventPublisher()
    }

    /*@Bean
    fun userDetailsService(): InMemoryUserDetailsManager {
        val user1: UserDetails = User.withUsername("user")
            .password(passwordEncoder().encode("user"))
            .roles("USER")
            .build()
        val admin: UserDetails = User.withUsername("admin")
            .password(passwordEncoder().encode("admin"))
            .roles("ADMIN", "USER")
            .build()
        return InMemoryUserDetailsManager(user1, admin)
    }*/
}
//    @Autowired
//    private lateinit var oauthUserService: CustomOAuth2UserService

//    @Bean
//    @Throws(Exception::class)
//    fun filterChain(http: HttpSecurity): SecurityFilterChain? {
//        http.authorizeHttpRequests().anyRequest().permitAll()
//            .requestMatchers("/", "/login", "/oauth/**").permitAll()
//            .anyRequest().authenticated()
//            .and()
//            .formLogin().permitAll()
//            .and()
//            .oauth2Login()
//            .loginPage("/login")
//            .userInfoEndpoint { oauthUserService }
//            .userService(oauthUserService)
//        return http.build()
/*http
    .authorizeRequests()
    .anyRequest().authenticated()
    .and()
    .oauth2Login()
    .authorizationEndpoint()
    .baseUri("/oauth2/authorize-client")
    .authorizationRequestRepository(authorizationRequestRepository())
return http.build()*/
//    }

/*@Bean
fun authorizationRequestRepository(): AuthorizationRequestRepository<OAuth2AuthorizationRequest> {
    return HttpSessionOAuth2AuthorizationRequestRepository()
}*/

/* @Bean
 fun userDetailsService(): InMemoryUserDetailsManager {
     val user1: UserDetails = User.withUsername("user")
         .password(passwordEncoder().encode("user"))
         .roles("USER")
         .build()
     val admin: UserDetails = User.withUsername("admin1")
         .password(passwordEncoder().encode("admin1"))
         .roles("ADMIN", "USER")
         .build()
     return InMemoryUserDetailsManager(user1, admin)
 }



 @Bean
 fun webSecurityCustomizer(): WebSecurityCustomizer {
     return WebSecurityCustomizer { web: WebSecurity ->
         web.debug(true).ignoring()
             .requestMatchers("/css/**", "/js/**", "/img/**", "/lib/**", "/favicon.ico")
     }
 }

 @Bean
 @Throws(Exception::class)
 fun authenticationManager(
     http: HttpSecurity,
     bCryptPasswordEncoder: BCryptPasswordEncoder?,
     userDetailService: InMemoryUserDetailsManager?
 ): AuthenticationManager {
     return http.getSharedObject(AuthenticationManagerBuilder::class.java)
         .userDetailsService(userDetailService)
         .passwordEncoder(bCryptPasswordEncoder)
         .and()
         .build()
 }

 @Bean
 @Throws(Exception::class)
 fun filterChain(http: HttpSecurity): SecurityFilterChain {
     http
//            .csrf { it.disable() }
         .authorizeHttpRequests {
             it
//                    .requestMatchers(HttpMethod.DELETE).hasRole("ADMIN")
//                    .requestMatchers("/admin/**").hasAnyRole("ADMIN")
//                    .requestMatchers("/pets*").hasAnyRole("USER", "ADMIN")
//                    .requestMatchers("/login")
                 .requestMatchers("/pets")
                 .permitAll()
                 .anyRequest().authenticated()
//                     .requestMatchers("/secured").permitAll()
//                    .requestMatchers("/", "/login", "/logout", "/logout-success").permitAll()
//                    .requestMatchers( "/checkout", "/pets", "/search", "/secured").authenticated()
//                    .requestMatchers("/admin/**").hasAnyRole("ADMIN")
//                    .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN")
//                    .anyRequest().authenticated()
         }
         .formLogin {
             it.loginPage("/login")
//                    .loginProcessingUrl("/dologin")
                 .failureUrl("/login?error=true")
                 .defaultSuccessUrl("/pets", true)
                 .permitAll()
         }
//            .logout {
//                it.invalidateHttpSession(true).logoutSuccessUrl("/logout-success").deleteCookies("remove")
//                    .logoutSuccessHandler(logoutSuccessHandler())
//            }
         .httpBasic(Customizer.withDefaults())
         .sessionManagement {
             it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
         }

     return http.build()
 }


}*/