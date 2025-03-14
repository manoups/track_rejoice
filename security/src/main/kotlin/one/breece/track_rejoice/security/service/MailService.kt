package one.breece.track_rejoice.security.service

import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.core.userdetails.UserDetails

interface MailService {
    fun send(request: HttpServletRequest, token: String, user: UserDetails)
}