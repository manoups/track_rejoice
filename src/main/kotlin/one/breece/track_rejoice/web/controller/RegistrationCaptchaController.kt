package one.breece.track_rejoice.web.controller

import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import one.breece.track_rejoice.commands.UserCommand
import one.breece.track_rejoice.web.dto.AppUserDetails
import one.breece.track_rejoice.event.OnRegistrationCompleteEvent
import one.breece.track_rejoice.security.captcha.CaptchaServiceV3
import one.breece.track_rejoice.security.captcha.ICaptchaService
import one.breece.track_rejoice.service.UserService
import one.breece.track_rejoice.util.GenericResponse
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

 /*   @GetMapping("/form")
    fun showRegistrationFormV3(model: Model): String {
        // create model object to store form data
        model.addAttribute("user", UserCommand())
        return "register-v3"
    }*/

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
        eventPublisher.publishEvent(OnRegistrationCompleteEvent(registered, request.locale, getAppUrl(request)))
        return GenericResponse("success")
    }


    private fun getAppUrl(request: HttpServletRequest): String {
        return "http://" + request.serverName + ":" + request.serverPort + request.contextPath
    }
}