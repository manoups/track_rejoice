package one.breece.track_rejoice.security.controller

import jakarta.servlet.http.HttpServletRequest
import one.breece.track_rejoice.security.command.LoginCommand
import one.breece.track_rejoice.security.command.UserCommand
import one.breece.track_rejoice.security.service.PasswordResetTokenService
import one.breece.track_rejoice.security.service.VerificationTokenService
import one.breece.track_rejoice.security.service.TokenEnum
import one.breece.track_rejoice.security.service.UserService
import org.slf4j.LoggerFactory
import org.springframework.mail.MailAuthenticationException
import org.springframework.context.MessageSource
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.ModelAndView
import java.io.UnsupportedEncodingException
import java.util.*


@Controller
class RegistrationController(
    var messages: MessageSource,
    var userService: UserService,
    private val tokenService: VerificationTokenService,
    private val passwordResetTokenService: PasswordResetTokenService
) {
    private val LOGGER = LoggerFactory.getLogger(javaClass)

    @GetMapping("/login")
    fun login(
        request: HttpServletRequest,
        model: Model,
        @RequestParam("message-key") messageKey: Optional<String>,
        @RequestParam("error") error: Optional<String>
    ): String {
        val locale: Locale = request.locale
        model.addAttribute("lang", locale.language)
        messageKey.ifPresent {
            model.addAttribute("message", messages.getMessage(it, null,null, locale))
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
        model: ModelMap
    ): ModelAndView {
        val locale = request.locale
        try {
            tokenService.resendRegistrationTokenForm(existingToken, request)
        } catch (e: MailAuthenticationException) {
            LOGGER.debug("MailAuthenticationException", e)
            model.addAttribute("lang", locale.language)
            return ModelAndView("redirect:/emailError", model)
        } catch (e: Exception) {
            LOGGER.debug(e.localizedMessage, e)
            model.addAttribute("error", e.localizedMessage)
            model.addAttribute("lang", locale.language)
            return ModelAndView("redirect:/login", model)
        }
        model.addAttribute("message-key", "message.resendToken")
        model.addAttribute("lang", locale.language)
        return ModelAndView("redirect:/login", model)
    }

    // NON-API


    @GetMapping("/register/bad-user")
    fun badUser(
        request: HttpServletRequest,
        model: ModelMap,
        @RequestParam("message-key") messageKey: Optional<String>,
        @RequestParam("expired") expired: Optional<String>,
        @RequestParam("token") token: Optional<String>
    ): ModelAndView {
        val locale = request.locale
        messageKey.ifPresent {
            model.addAttribute("message", messages.getMessage(it, null, locale))
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
        if (result.first == TokenEnum.VALID) {
            // if (user.isUsing2FA()) {
            // model.addAttribute("qr", userService.generateQRUrl(user));
            // return "redirect:/qrcode.html?lang=" + locale.getLanguage();
            // }
            authWithoutPassword(result.second!!)
            request.getSession(true).setAttribute(SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext())
            model.addAttribute("message-key", "message.accountVerified")
            return ModelAndView("redirect:/", model)
        }

        model.addAttribute("message-key", "auth.message.${result.first.name.lowercase()}")
        model.addAttribute("expired", TokenEnum.EXPIRED == result.first)
        model.addAttribute("token", token)
        return ModelAndView("redirect:/register/bad-user", model)
    }

    @GetMapping("/password-change")
    fun showChangePasswordPage(model: ModelMap, @RequestParam("token") token: String): ModelAndView {
        val result = passwordResetTokenService.validatePasswordResetToken(token)

        if (result != TokenEnum.VALID) {
            val messageKey = "auth.message.${result.toString().lowercase()}"
            model.addAttribute("messageKey", messageKey)
            return ModelAndView("redirect:/login", model)
        } else {
            model.addAttribute("token", token)
            return ModelAndView("redirect:/password-update")
        }
    }

    @GetMapping("/password-update")
    fun updatePassword(
        request: HttpServletRequest,
        model: ModelMap,
        @RequestParam("messageKey") messageKey: Optional<String?>
    ): ModelAndView {
        val locale = request.locale
        model.addAttribute("lang", locale.language)
        messageKey.ifPresent {
            model.addAttribute("message", messages.getMessage(it, null, locale))
        }
        return ModelAndView("password-update", model)
    }



    // ============== NON-API ============
    fun authWithoutPassword(user: UserDetails) {
        val authentication: Authentication = UsernamePasswordAuthenticationToken(user, null, user.authorities)
        SecurityContextHolder.getContext().authentication = authentication
    }


}