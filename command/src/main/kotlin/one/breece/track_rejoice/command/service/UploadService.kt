package one.breece.track_rejoice.command.service

import jakarta.transaction.Transactional
import one.breece.track_rejoice.command.domain.Bicycle
import one.breece.track_rejoice.command.domain.Item
import one.breece.track_rejoice.command.domain.Pet
import one.breece.track_rejoice.command.dto.PhotoResponse
import one.breece.track_rejoice.command.repository.BeOnTheLookOutRepository
import one.breece.track_rejoice.command.service.impl.AzureStorageService
import one.breece.track_rejoice.core.command.PhotoDescriptor
import one.breece.track_rejoice.security.service.UtilService
import org.apache.commons.io.FilenameUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.context.SecurityContext
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.*

@Service
@Transactional
class UploadService(
    private val azureStorageService: AzureStorageService,
    private val utilService: UtilService,
    private val repository: BeOnTheLookOutRepository
) {

    @Value("\${aws.s3.bucket}")
    lateinit var containerName: String

    fun upload(file: MultipartFile, sku: UUID, context: SecurityContext): PhotoResponse {
        val findBySku = repository.findBySku(sku)
        if (findBySku.isEmpty) {
            throw RuntimeException("SKU not found: $sku")
        }
        val user = utilService.getUsername(context)
        val name = "${user}/${System.currentTimeMillis()}.${FilenameUtils.getExtension(file.originalFilename)}"
        val bytes = file.bytes
        // Put the file into the bucket.
        val putObject = azureStorageService.putObject(bytes, containerName, name)
        val photoDescriptor = PhotoDescriptor(putObject.toString(), name)
        val beOnTheLookOut = findBySku.get()
        val redirect = when (beOnTheLookOut) {
            is Pet -> "pet"
            is Bicycle -> "bicycle"
            is Item -> "item"
            else -> throw RuntimeException("Unknown type: ${beOnTheLookOut::class.java.name}")
        }
        beOnTheLookOut.addPhoto(containerName, name)
        repository.save(beOnTheLookOut)
        return PhotoResponse(name, photoDescriptor, redirect)
    }

    fun deleteImage(sku: UUID, key: String) {
        val findBySku = repository.findBySku(sku)
        if (findBySku.isEmpty) {
            throw RuntimeException("SKU not found: $sku")
        }
        val beOnTheLookOut = findBySku.get()
        val removePhoto = beOnTheLookOut.removePhoto(key) ?: throw RuntimeException("Photo not found: $key")
        repository.save(beOnTheLookOut)
        azureStorageService.deleteObject(containerName, removePhoto.key)
    }

}
