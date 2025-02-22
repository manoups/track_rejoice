package one.breece.track_rejoice.service

import one.breece.track_rejoice.web.dto.PetResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.*

interface PetService {
    fun findAllByLngLat(lng: Double, lat: Double, distanceInMeters: Double, pageRequest: Pageable): Page<PetResponse>
    fun findById(petId: Long): Optional<PetResponse>
    fun findDistanceBetween(id1: Long, id2: Long): Double
    fun findAll(): List<PetResponse>
}