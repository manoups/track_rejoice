package one.breece.track_rejoice.command.service

import jakarta.servlet.http.HttpServletRequest
import java.util.*

interface APBService<in APBCommand, out APBResponse> {
    fun createBolo(announcementCommand: APBCommand, request: HttpServletRequest): APBResponse
    fun readBySku(sku: UUID): APBResponse
}
