package one.breece.track_rejoice.service.impl

import jakarta.transaction.Transactional
import one.breece.track_rejoice.domain.Pet
import one.breece.track_rejoice.domain.SpeciesEnum
import one.breece.track_rejoice.domain.Trace
import one.breece.track_rejoice.web.dto.CreatePetRequest
import one.breece.track_rejoice.web.dto.PetResponse
import one.breece.track_rejoice.repository.PetRepository
import one.breece.track_rejoice.service.PetService
import org.springframework.core.convert.converter.Converter
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*

@Service
class PetServiceImpl(
    private val repository: PetRepository,
    private val petToPetResponseMapper: Converter<Pet, PetResponse>
) : PetService {
    @Transactional
    fun invoke(requestBody: CreatePetRequest): PetResponse {
        val newPet = Pet(
            name = requestBody.name,
            lastSeenLocation = requestBody.lastSeenLocation,
            species = SpeciesEnum.valueOf(requestBody.species),
            breed = requestBody.breed,
            color = requestBody.color,
        )
        newPet.traceHistory.add(Trace(location = requestBody.lastSeenLocation))

        val geofence = repository.save(newPet)
        return petToPetResponseMapper.convert(geofence)!!
    }

    override fun findAllByLngLat(lng: Double, lat: Double, distanceInMeters: Double, pageRequest: Pageable): Page<PetResponse> {
        return repository.findAllByLngLat(lng, lat, distanceInMeters, pageRequest).map { petToPetResponseMapper.convert(it)!! }
    }

    override fun findById(petId: Long): Optional<PetResponse> {
        return repository.findById(petId).map { petToPetResponseMapper.convert(it) }
    }

    override fun findDistanceBetween(id1: Long, id2: Long) = repository.findDistanceBetween(id1, id2)

    override fun findAll(): List<PetResponse> {
        return repository.findAll().map { petToPetResponseMapper.convert(it)!! }
    }
}