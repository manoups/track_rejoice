package one.breece.track_rejoice.security.error

import one.breece.track_rejoice.security.util.GenericResponse
import org.springframework.context.MessageSource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.mail.MailAuthenticationException
import org.springframework.validation.BindException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import org.springframework.security.core.userdetails.UsernameNotFoundException

@RestControllerAdvice
class RestResponseEntityExceptionHandler(private val messages: MessageSource) : ResponseEntityExceptionHandler() {
    // API
    // 400
    protected fun handleBindException(
        ex: BindException,
        headers: HttpHeaders?,
        status: HttpStatus?,
        request: WebRequest
    ): ResponseEntity<Any>? {
        logger.error("400 Status Code", ex)
        val result = ex.bindingResult
        val bodyOfResponse: GenericResponse = GenericResponse(result.allErrors, "Invalid" + result.objectName)
        return handleExceptionInternal(ex, bodyOfResponse, HttpHeaders(), HttpStatus.BAD_REQUEST, request)
    }

    protected fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders?,
        status: HttpStatus?,
        request: WebRequest
    ): ResponseEntity<Any>? {
        logger.error("400 Status Code", ex)
        val result = ex.bindingResult
        val bodyOfResponse = GenericResponse(result.allErrors, "Invalid" + result.objectName)
        return handleExceptionInternal(ex, bodyOfResponse, HttpHeaders(), HttpStatus.BAD_REQUEST, request)
    }

    /* @ExceptionHandler(InvalidOldPasswordException::class)
     fun handleInvalidOldPassword(ex: RuntimeException, request: WebRequest): ResponseEntity<Any>? {
         logger.error("400 Status Code", ex)
         val bodyOfResponse: GenericResponse = GenericResponse(
             messages.getMessage("message.invalidOldPassword", null, request.locale),
             "InvalidOldPassword"
         )
         return handleExceptionInternal(ex, bodyOfResponse, HttpHeaders(), HttpStatus.BAD_REQUEST, request)
     }*/

    @ExceptionHandler(ReCaptchaInvalidException::class)
    fun handleReCaptchaInvalid(ex: RuntimeException, request: WebRequest): ResponseEntity<Any>? {
        logger.error("400 Status Code", ex)
        val bodyOfResponse =
            GenericResponse(messages.getMessage("message.invalidReCaptcha", null, request.locale), "InvalidReCaptcha")
        return handleExceptionInternal(ex, bodyOfResponse, HttpHeaders(), HttpStatus.BAD_REQUEST, request)
    }

    // 404
    @ExceptionHandler(UsernameNotFoundException::class)
    fun handleUserNotFound(ex: RuntimeException, request: WebRequest): ResponseEntity<Any>? {
        logger.error("404 Status Code", ex)
        val bodyOfResponse =
            GenericResponse("message.userNotFound", "UserNotFound")
        return handleExceptionInternal(ex, bodyOfResponse, HttpHeaders(), HttpStatus.NOT_FOUND, request)
    }

    // 409
    @ExceptionHandler(value = [UserAlreadyExistException::class])
    fun handleUserAlreadyExist(ex: RuntimeException, request: WebRequest): ResponseEntity<Any>? {
        logger.error("409 Status Code", ex)
        val bodyOfResponse =
            GenericResponse(messages.getMessage("message.regError", null, request.locale), "UserAlreadyExist")
        return handleExceptionInternal(ex, bodyOfResponse, HttpHeaders(), HttpStatus.CONFLICT, request)
    }

    // 500
    @ExceptionHandler(value = [MailAuthenticationException::class])
    fun handleMail(ex: RuntimeException?, request: WebRequest): ResponseEntity<Any> {
        logger.error("500 Status Code", ex)
        val bodyOfResponse =
            GenericResponse(messages.getMessage("message.email.config.error", null, request.locale), "MailError")
        return ResponseEntity<Any>(bodyOfResponse, HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(value = [ReCaptchaUnavailableException::class])
    fun handleReCaptchaUnavailable(ex: RuntimeException, request: WebRequest): ResponseEntity<Any>? {
        logger.error("500 Status Code", ex)
        val bodyOfResponse = GenericResponse(
            messages.getMessage("message.unavailableReCaptcha", null, request.locale),
            "InvalidReCaptcha"
        )
        return handleExceptionInternal(ex, bodyOfResponse, HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request)
    }

    @ExceptionHandler(value = [Exception::class])
    fun handleInternal(ex: RuntimeException?, request: WebRequest): ResponseEntity<Any> {
        logger.error("500 Status Code", ex)
        val bodyOfResponse =
            GenericResponse(messages.getMessage("message.error", null, request.locale), "InternalError")
        return ResponseEntity<Any>(bodyOfResponse, HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR)
    }
}