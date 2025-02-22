package one.breece.track_rejoice.web.controller

import one.breece.track_rejoice.web.error.UserAlreadyExistException
import one.breece.track_rejoice.util.GenericResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ExampleController {
    @PostMapping("/example")
    fun post(): ResponseEntity<String> {
        throw UserAlreadyExistException("In here!")
    }

//    @ExceptionHandler(UserAlreadyExistException::class)
    fun handleContentNotAllowedException(cnae: UserAlreadyExistException): ResponseEntity<GenericResponse> {

        val response = GenericResponse(cnae.message!!)
        return ResponseEntity<GenericResponse>(response, HttpStatus.BAD_REQUEST)
    }
}