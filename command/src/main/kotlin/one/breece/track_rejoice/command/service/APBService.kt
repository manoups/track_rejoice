package one.breece.track_rejoice.command.service

import java.util.*

interface APBService<in APBCommand, out APBResponse> {
    fun createBolo(announcementCommand: APBCommand): APBResponse
    fun deleteById(id: Long)
    fun readBySku(sku: UUID): APBResponse
}
