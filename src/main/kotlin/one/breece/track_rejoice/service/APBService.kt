package one.breece.track_rejoice.service

import java.util.*

interface APBService<in APBCommand, out APBResponse> {
    fun createAPB(announcementCommand: APBCommand): APBResponse
    fun deleteById(id: Long)
    fun readById(id: Long): APBResponse?
    fun readBySku(sku: UUID): APBResponse
}
