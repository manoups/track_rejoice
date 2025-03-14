package one.breece.track_rejoice.security.web.controller

import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import one.breece.track_rejoice.security.command.UserCommand
import one.breece.track_rejoice.security.captcha.CaptchaServiceV3
import one.breece.track_rejoice.security.captcha.ICaptchaService
import one.breece.track_rejoice.security.domain.AppUserDetails
import one.breece.track_rejoice.security.event.OnRegistrationCompleteEvent
import one.breece.track_rejoice.security.service.UserService
import one.breece.track_rejoice.security.util.GenericResponse
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationEventPublisher
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/register")
class RegistrationCaptchaController(private val userService: UserService,
                                    private val captchaServiceV3: ICaptchaService,
                                    private var eventPublisher: ApplicationEventPublisher
) {
    private val LOGGER = LoggerFactory.getLogger(javaClass)

    @PostMapping("/v3")
    fun captchaV3RegisterUserAccount(
        userCommand: @Valid UserCommand,
        request: HttpServletRequest
    ): GenericResponse {

        val response = request.getParameter("response")
        captchaServiceV3.processResponse(response, CaptchaServiceV3.REGISTER_ACTION)
        return registerNewUserHandler(userCommand, request)
    }

    private fun registerNewUserHandler(userCommand: UserCommand, request: HttpServletRequest): GenericResponse {
        LOGGER.debug("Registering user account with information: {}", userCommand)

        val registered: AppUserDetails = userService.saveUser(userCommand)
        eventPublisher.publishEvent(OnRegistrationCompleteEvent(registered, request))
        return GenericResponse("success")
    }
}