package one.breece.track_rejoice.configuration


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

class CustomOAuth2User(private val oauth2User: OAuth2User ): OAuth2User {

    override fun getAttributes(): Map<String, Any> {
        return oauth2User.attributes
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return oauth2User.authorities
    }

    override fun getName(): String? {
        return oauth2User.getAttribute("name")
    }

    fun getEmail(): String? {
        return oauth2User.getAttribute("email")
    }
}