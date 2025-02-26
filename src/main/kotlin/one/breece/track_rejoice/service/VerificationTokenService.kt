package one.breece.track_rejoice.service

import jakarta.servlet.http.HttpServletRequest

interface VerificationTokenService {
    fun resendRegistrationTokenForm(existingToken: String, request: HttpServletRequest)

}
