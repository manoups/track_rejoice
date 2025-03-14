package one.breece.track_rejoice.command.web.controller

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import kotlinx.coroutines.runBlocking
import one.breece.track_rejoice.command.dto.BucketItem
import one.breece.track_rejoice.command.service.impl.S3Service
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
@RequestMapping("/aws")
class AWSController(val s3Service: S3Service) {

    // Change to your Bucket Name.
    @Value("\${aws.s3.bucket}")
    lateinit var bucketName: String


    /*@Autowired
    var recService: AnalyzePhotos? = null

    @Autowired
    var excel: WriteExcel? = null*/

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
    fun getImages(request: HttpServletRequest?, response: HttpServletResponse?): String? = runBlocking {
        return@runBlocking s3Service.ListAllObjects(bucketName)
    }

    @GetMapping(value = ["/listimages"])
    fun listImages(request: HttpServletRequest?, response: HttpServletResponse?): ResponseEntity<List<BucketItem>> =
        runBlocking {
            return@runBlocking ResponseEntity(s3Service.loi(bucketName), HttpStatus.OK)
        }

    @GetMapping(value = ["/listBucketObjects"])
    fun listBuckets(model: Model): String {
        val runBlocking = runBlocking {
            return@runBlocking s3Service.listBucketObjects(bucketName)
        }
        model.addAttribute("buckets", runBlocking)
        return "list-contents"
    }
}
/*

    // Generates a report that analyzes photos in a given bucket.
    @GetMapping(value = ["/report"])
    @ResponseBody
    fun report(request: HttpServletRequest, response: HttpServletResponse) = runBlocking {

        // Get a list of key names in the given bucket.
        val myKeys = s3Service?.listBucketObjects(bucketName)

        // loop through each element in the List.
        val myList = mutableListOf<List<*>>()
        val len = myKeys?.size
        for (z in 0 until len!!) {
            val key = myKeys?.get(z) as String
            val keyData = s3Service?.getObjectBytes(bucketName, key)

            //Analyze the photo.
            val item = recService?.DetectLabels(keyData, key)
            if (item != null) {
                myList.add(item)
            }
        }

        // Now we have a list of WorkItems describing the photos in the S3 bucket.
        val excelData = excel?.exportExcel(myList)
        try {

            // Download the report.
            val reportName  = "ExcelReport.xls"
            response.contentType  = "application/vnd.ms-excel"
            response.setHeader("Content-disposition", "attachment; filename=$reportName")
            org.apache.commons.io.IOUtils.copy(excelData, response?.outputStream)
            response.flushBuffer()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // Downloads the given image from the Amazon S3 bucket.
    @GetMapping(value = ["/downloadphoto"])
    fun fileDownload(request: HttpServletRequest, response: HttpServletResponse)  = runBlocking {
        try {
            val photoKey = request.getParameter("photoKey")
            val photoBytes: ByteArray? = s3Service?.getObjectBytes(bucketName, photoKey)
            val `is`: InputStream = ByteArrayInputStream(photoBytes)

            // Define the required information here.
            response.contentType = "image/png"
            response.setHeader("Content-disposition", "attachment; filename=$photoKey")
            org.apache.commons.io.IOUtils.copy(`is`, response.outputStream)
            response.flushBuffer()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // Upload a photo to an Amazon S3 bucket.
    @PostMapping(value = ["/upload"])
    @ResponseBody
    fun singleFileUpload(@RequestParam("file") file: MultipartFile): ModelAndView? = runBlocking {
        try {
            val bytes = file.bytes
            val name = file.originalFilename

            // Put the file into the bucket.
            s3Service?.putObject(bytes, bucketName, name)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return@runBlocking ModelAndView(RedirectView("photo"))
    }
}*/
