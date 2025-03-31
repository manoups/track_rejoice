package one.breece.track_rejoice.command.web.controller

import one.breece.track_rejoice.command.service.UploadService
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

    private val uploadService: UploadService
) {



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
    ): String {
        var redirect = ""
        try {
           val response = uploadService.upload(file, sku, context)
            redirect = response.redirect
            model.addAttribute("msg", "Uploaded images: ${response.name}")
            model.addAttribute("photos", listOf( response.photoDescriptor))
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return "redirect:bolo/form/$redirect/created/$sku"
    }
}