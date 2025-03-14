package one.breece.track_rejoice.query.service.impl

import jakarta.transaction.Transactional
import one.breece.track_rejoice.core.command.PetResponseCommand
import one.breece.track_rejoice.query.domain.Pet
import one.breece.track_rejoice.query.repository.PetQueryRepository
import one.breece.track_rejoice.query.service.PetQueryService
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Service
import java.util.*

@Service
class PetQueryServiceImpl(
    private val repository: PetQueryRepository,
    private val petToQueryPetResponseCommand: Converter<Pet, PetResponseCommand>
) : PetQueryService {
    @Transactional
    override fun readBySku(sku: UUID): PetResponseCommand {
        return repository.readBySku(sku).map { petToQueryPetResponseCommand.convert(it) }
            .orElseThrow { NoSuchElementException() }
    }
}