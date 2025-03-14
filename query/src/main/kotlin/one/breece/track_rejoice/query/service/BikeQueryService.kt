package one.breece.track_rejoice.query.service

import one.breece.track_rejoice.core.command.BicycleResponseCommand
import java.util.*

interface BikeQueryService {
    fun readBySku(sku: UUID): BicycleResponseCommand
}
