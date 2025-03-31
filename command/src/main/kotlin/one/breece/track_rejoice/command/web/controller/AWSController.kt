package one.breece.track_rejoice.command.web.controller

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import one.breece.track_rejoice.command.service.impl.AzureStorageService
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
@RequestMapping("/aws")
class AWSController(val azureStorageService: AzureStorageService) {

    // Change to your Bucket Name.
    @Value("\${aws.s3.bucket}")
    lateinit var bucketName: String

    @GetMapping("/process")
    fun process(): String? {
        return "process"
    }

    @GetMapping("/photo")
    fun photo(): String? {
        return "upload"
    }

    @GetMapping("/")
    fun root(): String? {
        return "index"
    }

    @GetMapping(value = ["/getimages"])
    @ResponseBody
    fun getImages(request: HttpServletRequest?, response: HttpServletResponse?): List<String> =
         azureStorageService.listBucketObjects(bucketName)

    @GetMapping(value = ["/listBucketObjects"])
    fun listBuckets(model: Model): String {
        val listBuckets = azureStorageService.listBucketObjects(bucketName)
        model.addAttribute("buckets", listBuckets)
        return "list-contents"
    }
}

