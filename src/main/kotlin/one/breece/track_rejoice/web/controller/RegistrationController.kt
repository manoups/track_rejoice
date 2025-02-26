package one.breece.track_rejoice.web.controller

import jakarta.servlet.http.HttpServletRequest
import one.breece.track_rejoice.commands.LoginCommand
import one.breece.track_rejoice.commands.UserCommand
import one.breece.track_rejoice.service.UserService
import one.breece.track_rejoice.service.VerificationTokenService
import one.breece.track_rejoice.service.impl.TokenEnum
import org.slf4j.LoggerFactory
import org.springframework.context.MessageSource
import org.springframework.mail.MailAuthenticationException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import java.io.UnsupportedEncodingException
import java.util.*


@Controller
class RegistrationController(
    var messages: MessageSource,
    var userService: UserService,
    private val tokenService: VerificationTokenService
) {
    private val LOGGER = LoggerFactory.getLogger(javaClass)

    @RequestMapping("/login")
    fun login(
        request: HttpServletRequest,
        model: Model,
        @RequestParam("messageKey") messageKey: Optional<String>,
        @RequestParam("error") error: Optional<String>
    ): String {
        val locale: Locale = request.locale
        model.addAttribute("lang", locale.language)
        messageKey.ifPresent {
            model.addAttribute("message", messages.getMessage(it, null, locale))
        }
        error.ifPresent { model.addAttribute("error", it) }
        model.addAttribute("loginCommand", LoginCommand())
        return "login"
    }

    @GetMapping("/emailError")
    fun emailError(): String = "emailError"

    @GetMapping("/register-v3")
    fun showRegistrationFormV3(model: Model): String {
        // create model object to store form data
        model.addAttribute("user", UserCommand())
        return "register-v3"
    }

    @GetMapping("/register/resend-registration-token")
    fun resendRegistrationTokenForm(
        @RequestParam("token") existingToken: String,
        request: HttpServletRequest,
        model: Model,
        redirectAttributes: RedirectAttributes
    ): String {
        val locale = request.locale
        try {
            tokenService.resendRegistrationTokenForm(existingToken, request)
        } catch (e: MailAuthenticationException) {
            LOGGER.debug("MailAuthenticationException", e)
            redirectAttributes.addAttribute("lang", locale.language)
            return "redirect:/emailError"
        } catch (e: Exception) {
            LOGGER.debug(e.localizedMessage, e)
            redirectAttributes.addAttribute("messageKey", e.localizedMessage)
            redirectAttributes.addAttribute("lang", locale.language)
            return "redirect:/login"
        }
        redirectAttributes.addAttribute("messageKey", "message.resendToken")
        redirectAttributes.addAttribute("lang", locale.language)
        return "redirect:/login"
    }

    // NON-API


    @GetMapping("/register/bad-user")
    fun badUser(
        request: HttpServletRequest,
        model: ModelMap,
        @RequestParam("messageKey") messageKey: Optional<String>,
        @RequestParam("expired") expired: Optional<String>,
        @RequestParam("token") token: Optional<String>
    ): ModelAndView {
        val locale = request.locale
        messageKey.ifPresent {
            val message = messages.getMessage(it, null, locale)
            model.addAttribute("message", message)
        }

        expired.ifPresent { model.addAttribute("expired", it) }
        token.ifPresent { model.addAttribute("token", it) }

        return ModelAndView("badUser", model)
    }

    @GetMapping("/register/confirm")
    @Throws(UnsupportedEncodingException::class)
    fun confirmRegistration(
        request: HttpServletRequest,
        model: ModelMap,
        @RequestParam("token") token: String
    ): ModelAndView {
        val locale = request.locale
        model.addAttribute("lang", locale.language)
        val result = userService.validateVerificationToken(token)
        if (result == TokenEnum.VALID) {
            val user = userService.loadUserByUsername(token)
            // if (user.isUsing2FA()) {
            // model.addAttribute("qr", userService.generateQRUrl(user));
            // return "redirect:/qrcode.html?lang=" + locale.getLanguage();
            // }
            authWithoutPassword(user)
            model.addAttribute("messageKey", "message.accountVerified")
            return ModelAndView("redirect:/", model)
        }

        model.addAttribute("messageKey", "auth.message.${result.name.lowercase()}")
        model.addAttribute("expired", TokenEnum.EXPIRED == result)
        model.addAttribute("token", token)
        return ModelAndView("redirect:/register/bad-user", model)
    }

    @GetMapping("register/success")
    fun registerSuccess(): String {
        return "successRegister"
    }

    // ============== NON-API ============
    fun authWithoutPassword(user: UserDetails) {
        val authentication: Authentication = UsernamePasswordAuthenticationToken(user, null, user.authorities)
        SecurityContextHolder.getContext().authentication = authentication
    }


}