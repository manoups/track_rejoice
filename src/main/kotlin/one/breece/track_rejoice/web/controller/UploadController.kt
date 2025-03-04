package one.breece.track_rejoice.web.controller

import kotlinx.coroutines.runBlocking
import one.breece.track_rejoice.service.S3Service
import one.breece.track_rejoice.service.UtilService
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
    private val utilService: UtilService
) {
    @Value("\${aws.s3.bucket}")
    lateinit var bucketName: String


    @GetMapping("/uploadimage")
    fun displayUploadForm(): String {
        return "imageupload/index"
    }

    @PostMapping(value = ["/upload"])
    fun singleFileUpload(
        model: Model,
        @RequestParam("image") file: MultipartFile,
        @CurrentSecurityContext context: SecurityContext
    ): String = runBlocking {
        try {
            val user = utilService.getUsername(context)
            val name = "${user}/${Date()}/${file.originalFilename}"
            val bytes = file.bytes
            // Put the file into the bucket.
            s3Service.putObject(bytes, bucketName, name)
            model.addAttribute("msg", "Uploaded images: $name")
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return@runBlocking "imageupload/index"
    }
}