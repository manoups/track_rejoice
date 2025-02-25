package one.breece.track_rejoice.service

import one.breece.track_rejoice.commands.APBCommand
import one.breece.track_rejoice.commands.APBResponse
import one.breece.track_rejoice.web.dto.PetResponse

interface APBService {
    fun createAPB(apbCommand: APBCommand): APBResponse
    fun deleteById(id: Long)
}
