package one.breece.track_rejoice.web.controller

import kotlinx.coroutines.runBlocking
import one.breece.track_rejoice.repository.command.BeOnTheLookOutRepository
import one.breece.track_rejoice.security.util.GenericResponse
import one.breece.track_rejoice.service.S3Service
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class UploadRestController(private val s3Service: S3Service,
                           private val repository: BeOnTheLookOutRepository
) {
    @Value("\${aws.s3.bucket}")
    lateinit var bucketName: String

    @DeleteMapping("/delete/{sku}/{key}")
    fun deleteImage(
        @PathVariable sku: UUID,
        @PathVariable key: String
    ): GenericResponse = runBlocking {
        val findBySku = repository.findBySku(sku)
        if (findBySku.isEmpty) {
            throw RuntimeException("SKU not found: $sku")
        }
        val beOnTheLookOut = findBySku.get()
        val removePhoto = beOnTheLookOut.removePhoto(key) ?: throw RuntimeException("Photo not found: $key")
        repository.save(beOnTheLookOut)
        s3Service.deleteObject(bucketName, removePhoto.key)
        return@runBlocking GenericResponse("Image deleted successfully")
    }

    @PostMapping("/rotate/{sku}/{key}")
    fun rotateImage(
        @PathVariable sku: UUID,
        @PathVariable key: String
    ): GenericResponse = runBlocking {
        val findBySku = repository.findBySku(sku)
        if (findBySku.isEmpty) {
            throw RuntimeException("SKU not found: $sku")
        }
        val beOnTheLookOut = findBySku.get()
        val rotatePhoto = beOnTheLookOut.findPhoto(key) ?: throw RuntimeException("Photo not found: $key")
        return@runBlocking GenericResponse("Image rotated successfully")
    }
}