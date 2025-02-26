package one.breece.track_rejoice.service

import org.springframework.http.ResponseEntity

interface UtilService {

    fun addHumanReadableAddress(id: Long): ResponseEntity<String>
}
