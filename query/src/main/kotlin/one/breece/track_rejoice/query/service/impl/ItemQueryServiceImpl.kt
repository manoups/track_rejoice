package one.breece.track_rejoice.query.service.impl

import jakarta.transaction.Transactional
import one.breece.track_rejoice.core.command.ItemResponseCommand
import one.breece.track_rejoice.query.domain.Item
import one.breece.track_rejoice.query.repository.ItemQueryRepository
import one.breece.track_rejoice.query.service.ItemQueryService
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Service
import java.util.*

@Service
class ItemQueryServiceImpl(
    private val repository: ItemQueryRepository,
    private val itemToQueryItemResponseCommand: Converter<Item, ItemResponseCommand>
) : ItemQueryService {
    @Transactional
    override fun readBySku(sku: UUID): ItemResponseCommand {
        return repository.readBySku(sku).map { itemToQueryItemResponseCommand.convert(it) }
            .orElseThrow { NoSuchElementException() }
    }
}