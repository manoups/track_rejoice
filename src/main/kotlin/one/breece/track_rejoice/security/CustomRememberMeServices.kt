package one.breece.track_rejoice.security

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import one.breece.track_rejoice.domain.AppUser
import one.breece.track_rejoice.repository.UserRepository
import org.springframework.security.authentication.AuthenticationDetailsSource
import org.springframework.security.authentication.RememberMeAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository
import java.util.*

class CustomRememberMeServices(
    private val key: String,
    userDetailsService: UserDetailsService,
    private val tokenRepository: PersistentTokenRepository,
    private val userRepository: UserRepository
) : PersistentTokenBasedRememberMeServices(key, userDetailsService, tokenRepository) {

    private val authoritiesMapper: GrantedAuthoritiesMapper = NullAuthoritiesMapper()
    private val authenticationDetailsSource: AuthenticationDetailsSource<HttpServletRequest, *> =
        WebAuthenticationDetailsSource()

    override fun onLoginSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        successfulAuthentication: Authentication
    ) {
        val username: String = (successfulAuthentication.principal as AppUser).username
        logger.debug("Creating new persistent login for user $username")
        val persistentToken = PersistentRememberMeToken(username, generateSeriesData(), generateTokenData(), Date())
        try {
            tokenRepository.createNewToken(persistentToken)
            addCookie(persistentToken, request, response)
        } catch (e: Exception) {
            logger.error("Failed to save persistent token ", e)
        }
    }

    override fun createSuccessfulAuthentication(request: HttpServletRequest, user: UserDetails): Authentication {
        val auser= userRepository.findByUsername(user.username)
        val auth = RememberMeAuthenticationToken(key, auser, authoritiesMapper.mapAuthorities(user.authorities))
        auth.details = authenticationDetailsSource.buildDetails(request)
        return auth
    }

    private fun addCookie(
        token: PersistentRememberMeToken,
        request: HttpServletRequest,
        response: HttpServletResponse
    ) {
        setCookie(arrayOf(token.series, token.tokenValue), tokenValiditySeconds, request, response)
    }
}

