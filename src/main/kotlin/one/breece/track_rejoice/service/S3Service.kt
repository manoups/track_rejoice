package one.breece.track_rejoice.service

import aws.sdk.kotlin.services.s3.S3Client
import aws.sdk.kotlin.services.s3.model.*
import aws.smithy.kotlin.runtime.content.ByteStream
import aws.smithy.kotlin.runtime.content.toByteArray
import one.breece.track_rejoice.web.dto.BucketItem
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.w3c.dom.Document
import java.io.StringWriter
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.parsers.ParserConfigurationException
import javax.xml.transform.TransformerException
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult
import kotlin.system.exitProcess
import aws.sdk.kotlin.services.s3.model.ObjectIdentifier

@Component
class S3Service {

    @Value("\${aws.region}")
    lateinit var REGION: String

    var myBytes: ByteArray? = null

    // Returns the names of all images in the given bucket.
    suspend fun listBucketObjects(bucketName: String?): List<String> {

        val listObjects = ListObjectsRequest {
            bucket = bucketName
        }

        S3Client { region = REGION }.use {
            val response = it.listObjects(listObjects)
            return response.contents?.map { myObject ->
                return@map myObject.key.toString()
            } ?: emptyList()
        }
    }

    // Returns the names of all images and data within an XML document.
    suspend fun ListAllObjects(bucketName: String): String? {
        return convertToString(toXml(loi(bucketName)))
    }

    suspend fun loi(bucketName: String): List<BucketItem> {

        val listObjects = ListObjectsRequest {
            bucket = bucketName
        }

        S3Client { region = REGION }.use {
            val res = it.listObjects(listObjects)
            return res.contents?.map { myObject ->
                return@map BucketItem(
                    myObject.key,
                    myObject.owner?.displayName.toString(),
                    (myObject.size!! / 1024).toString(),
                    myObject.lastModified.toString()
                )

                // Push the items to the list.
                //                bucketItems.add(myItem)
            } ?: emptyList()
//            return bucketItems
        }
    }

    suspend fun deleteObject(data: ByteArray, bucketName: String?, objectKey: String?): String? {
        val request = DeleteObjectRequest {
            bucket = bucketName
            key = objectKey
        }

        S3Client {
            region = REGION
        }.use { s3Client ->
            val response = s3Client.deleteObject(request)
            return response.versionId
        }
    }


    // Places an image into an Amazon S3 bucket.
    suspend fun putObject(data: ByteArray, bucketName: String?, objectKey: String?): String? {
        val request = PutObjectRequest {
            bucket = bucketName
            key = objectKey
            body = ByteStream.fromBytes(data)
        }

        S3Client {
            region = REGION
        }.use { s3Client ->
            val response = s3Client.putObject(request)
            return response.eTag
        }
    }

    // Get the byte[] from this Amazon S3 object.
    suspend fun getObjectBytes(bucketName: String?, keyName: String?): ByteArray? {
        val objectRequest = GetObjectRequest {
            key = keyName
            bucket = bucketName
        }

        S3Client { region = REGION }.use { s3Client ->
            s3Client.getObject(objectRequest) { resp ->
                myBytes = resp.body?.toByteArray()
            }
            return myBytes
        }
    }

    // Convert items into XML to pass back to the view.
    private fun toXml(itemList: List<BucketItem>): Document {
        try {
            val factory = DocumentBuilderFactory.newInstance()
            val builder = factory.newDocumentBuilder()
            val doc = builder.newDocument()

            // Start building the XML.
            val root = doc.createElement("Items")
            doc.appendChild(root)

            // Get the elements from the collection.
            val custCount = itemList.size

            // Iterate through the collection.
            for (index in 0 until custCount) {

                // Get the WorkItem object from the collection.
                val myItem = itemList[index]
                val item = doc.createElement("Item")
                root.appendChild(item)

                // Set Key.
                val id = doc.createElement("Key")
                id.appendChild(doc.createTextNode(myItem.key))
                item.appendChild(id)

                // Set Owner.
                val name = doc.createElement("Owner")
                name.appendChild(doc.createTextNode(myItem.owner))
                item.appendChild(name)

                // Set Date.
                val date = doc.createElement("Date")
                date.appendChild(doc.createTextNode(myItem.date))
                item.appendChild(date)

                // Set Size.
                val desc = doc.createElement("Size")
                desc.appendChild(doc.createTextNode(myItem.size))
                item.appendChild(desc)
            }
            return doc
        } catch (e: ParserConfigurationException) {
            e.printStackTrace()
            exitProcess(0)
        }
    }

    private fun convertToString(xml: Document): String {
        try {
            val transformer = TransformerFactory.newInstance().newTransformer()
            val result = StreamResult(StringWriter())
            val source = DOMSource(xml)
            transformer.transform(source, result)
            return result.writer.toString()

        } catch (ex: TransformerException) {
            ex.printStackTrace()
            exitProcess(0)
        }
    }
}