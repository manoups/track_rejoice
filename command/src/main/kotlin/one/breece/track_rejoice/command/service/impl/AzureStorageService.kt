package one.breece.track_rejoice.command.service.impl

import com.azure.spring.cloud.core.resource.StorageBlobResource
import com.azure.storage.blob.BlobServiceClient
import org.springframework.stereotype.Service
import java.net.URL


@Service
class AzureStorageService(private val blobServiceClient: BlobServiceClient,) {


    fun putObject(qrCodeImage: ByteArray, bucket: String, key: String): URL {
        val storageBlobResource = StorageBlobResource(blobServiceClient, "azure-blob://$bucket/$key")
        storageBlobResource.outputStream.use { it.write(qrCodeImage) }
        return storageBlobResource.url
    }

    fun listBucketObjects(bucketName: String): List<String> {
        return blobServiceClient.getBlobContainerClient(bucketName).listBlobs().map { it.name }
    }

    fun deleteObject(bucketName: String, key: String) {
        blobServiceClient.getBlobContainerClient(bucketName).listBlobsByHierarchy(key).forEach { it.setDeleted(true) }
    }
}