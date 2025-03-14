package one.breece.track_rejoice.security.domain

import org.springframework.security.core.userdetails.UserDetails

interface AppUserDetails : UserDetails {
    val isUsing2FA: Boolean
}