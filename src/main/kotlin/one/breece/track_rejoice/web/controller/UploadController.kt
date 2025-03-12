package one.breece.track_rejoice.web.controller

import kotlinx.coroutines.runBlocking
import one.breece.track_rejoice.domain.command.Bicycle
import one.breece.track_rejoice.domain.command.Item
import one.breece.track_rejoice.domain.command.Pet
import one.breece.track_rejoice.repository.command.BeOnTheLookOutRepository
import one.breece.track_rejoice.service.S3Service
import one.breece.track_rejoice.service.UtilService
import one.breece.track_rejoice.web.dto.PhotoDescriptor
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.annotation.CurrentSecurityContext
import org.springframework.security.core.context.SecurityContext
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.util.*


@Controller
class UploadController(
    private val s3Service: S3Service,
    private val utilService: UtilService,
    private val repository: BeOnTheLookOutRepository
) {
    @Value("\${aws.s3.bucket}")
    lateinit var bucketName: String


    @Deprecated(message = "Use the new upload form")
    @GetMapping("/uploadimage")
    fun displayUploadForm(): String {
        return "imageupload/chatgpt"
    }

    @PostMapping(value = ["/upload"])
    fun singleFileUpload(
        model: Model,
        @RequestParam("image") file: MultipartFile,
        @RequestParam("sku") sku: UUID,
        @CurrentSecurityContext context: SecurityContext
    ): String = runBlocking {
        var redirect = ""
        try {
            val findBySku = repository.findBySku(sku)
            if (findBySku.isEmpty) {
                throw RuntimeException("SKU not found: $sku")
            }
            val user = utilService.getUsername(context)
            val name = "${user}/${file.originalFilename}"
            val bytes = file.bytes
            // Put the file into the bucket.
            val putObject = s3Service.putObject(bytes, bucketName, name)
            val photoDescriptor = PhotoDescriptor("https://$bucketName.s3.amazonaws.com/$name", name)
            val beOnTheLookOut = findBySku.get()
            redirect = when(beOnTheLookOut) {
                is Pet -> "pet"
                is Bicycle -> "bicycle"
                is Item -> "item"
                else -> throw RuntimeException("Unknown type: ${beOnTheLookOut::class.java.name}")
            }
            beOnTheLookOut.addPhoto(bucketName, name)
            repository.save(beOnTheLookOut)
            model.addAttribute("msg", "Uploaded images: $name")
            model.addAttribute("photos", listOf( photoDescriptor))
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return@runBlocking "redirect:bolo/form/$redirect/created/$sku"
    }
}