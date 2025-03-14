package one.breece.track_rejoice.query.service

import one.breece.track_rejoice.core.command.PetResponseCommand
import java.util.*

interface PetQueryService {
    fun readBySku(sku: UUID): PetResponseCommand
}