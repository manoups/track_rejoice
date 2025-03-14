package one.breece.track_rejoice.query.service

import one.breece.track_rejoice.core.command.ItemResponseCommand
import java.util.*

interface ItemQueryService {
    fun readBySku(sku: UUID): ItemResponseCommand

}