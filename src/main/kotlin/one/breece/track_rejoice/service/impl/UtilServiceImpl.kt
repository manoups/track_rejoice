package one.breece.track_rejoice.service.impl

import one.breece.track_rejoice.repository.command.PetRepository
import one.breece.track_rejoice.security.domain.AppUser
import one.breece.track_rejoice.service.GeocodingService
import one.breece.track_rejoice.service.UtilService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContext
import org.springframework.stereotype.Service

@Service
//@PreAuthorize("hasRole('ROLE_ADMIN')")
class UtilServiceImpl(private val petRepository: PetRepository, private val geocodingService: GeocodingService) :
    UtilService {

    override fun addHumanReadableAddress(id: Long): ResponseEntity<String> {
        val petOptional = petRepository.findById(id)
        if (petOptional.isEmpty) {
            return ResponseEntity.notFound().build()
        }
        val pet = petOptional.get()
        if (null != pet.humanReadableAddress) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(pet.humanReadableAddress.toString())
        }
        val reverseGeocode =
            geocodingService.reverseGeocode(pet.lastSeenLocation.y, pet.lastSeenLocation.x)
        pet.humanReadableAddress = reverseGeocode
        petRepository.save(pet)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    override fun getName(context: SecurityContext): String? {
        return if (context.authentication.principal is AppUser) {
            (context.authentication.principal as AppUser).firstName
        } else null
    }

    override fun getUsername(context: SecurityContext): String? {
        return if (context.authentication.principal is AppUser) {
            (context.authentication.principal as AppUser).username
        } else null
    }
}