package one.breece.track_rejoice.command.service

import one.breece.track_rejoice.command.command.PetAnnouncementCommand
import one.breece.track_rejoice.command.command.PetResponseCommand

interface PetService : APBService<PetAnnouncementCommand, PetResponseCommand> {
//    fun findAllByLngLat(lon: Double, lat: Double, distanceInMeters: Double, pageRequest: Pageable): Page<PetResponse>
//    fun findById(petId: Long): Optional<PetResponse>
//    fun findDistanceBetween(id1: Long, id2: Long): Double
//    fun findAll(): List<PetResponse>
}