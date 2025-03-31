package one.breece.track_rejoice.command.web.controller

import one.breece.track_rejoice.command.repository.BeOnTheLookOutRepository
import one.breece.track_rejoice.command.service.UploadService
import one.breece.track_rejoice.security.util.GenericResponse
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class UploadRestController(private val uploadService: UploadService,
                           private val repository: BeOnTheLookOutRepository
) {
    @DeleteMapping("/delete/{sku}/{key}")
    fun deleteImage(
        @PathVariable sku: UUID,
        @PathVariable key: String
    ): GenericResponse {
        uploadService.deleteImage(sku, key)
        return GenericResponse("Image deleted successfully")
    }

    @PostMapping("/rotate/{sku}/{key}")
    fun rotateImage(
        @PathVariable sku: UUID,
        @PathVariable key: String
    ): GenericResponse {
        val findBySku = repository.findBySku(sku)
        if (findBySku.isEmpty) {
            throw RuntimeException("SKU not found: $sku")
        }
        val beOnTheLookOut = findBySku.get()
        val rotatePhoto = beOnTheLookOut.findPhoto(key) ?: throw RuntimeException("Photo not found: $key")
        return GenericResponse("Image rotated successfully")
    }
}