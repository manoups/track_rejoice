package one.breece.track_rejoice.web.controller

import one.breece.track_rejoice.service.UtilService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/util")
class UtilController(private val utilService: UtilService) {
    @PostMapping("/{id}")
    fun addHumanReadableAddress(@PathVariable id: Long): ResponseEntity<String> {
        return utilService.addHumanReadableAddress(id)
    }
}