package one.breece.track_rejoice.query.service.impl

import jakarta.transaction.Transactional
import one.breece.track_rejoice.core.command.BicycleResponseCommand
import one.breece.track_rejoice.core.domain.BoloStateEnum
import one.breece.track_rejoice.query.domain.Bicycle
import one.breece.track_rejoice.query.repository.BicycleQueryRepository
import one.breece.track_rejoice.query.service.BikeQueryService
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Service
import java.util.*

@Service
class BikeQueryServiceImpl(
    private val repository: BicycleQueryRepository,
    private val bicycleToQueryBicycleResponseCommand: Converter<Bicycle, BicycleResponseCommand>
) : BikeQueryService {
    @Transactional
    override fun readBySku(sku: UUID): BicycleResponseCommand {
        return repository.readBySkuAndState(sku, BoloStateEnum.ACTIVE).map { bicycleToQueryBicycleResponseCommand.convert(it) }
            .orElseThrow { NoSuchElementException() }
    }
}