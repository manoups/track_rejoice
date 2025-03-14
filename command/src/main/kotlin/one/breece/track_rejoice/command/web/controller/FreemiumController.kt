package one.breece.track_rejoice.command.web.controller

import one.breece.track_rejoice.command.service.BoloService
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping

@Controller
@ConditionalOnProperty(name = ["payments"], havingValue = "disabled", matchIfMissing = true)
class FreemiumController(private val boloService: BoloService) {
    @PostMapping("/api/orders/{orderID}/{announcementId}/capture")
    fun captureOrders(@PathVariable announcementId: Long, @PathVariable orderID: String): ResponseEntity<String> {
        boloService.enableAnnouncement(announcementId)
        return ResponseEntity("OK", HttpStatus.OK)
    }

}