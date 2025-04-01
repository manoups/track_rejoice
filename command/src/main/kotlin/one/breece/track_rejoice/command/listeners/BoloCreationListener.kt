package one.breece.track_rejoice.command.listeners

import one.breece.track_rejoice.command.events.CreateQR
import one.breece.track_rejoice.command.repository.BeOnTheLookOutRepository
import one.breece.track_rejoice.command.service.impl.AzureStorageService
import one.breece.track_rejoice.command.service.impl.QRCodeGeneratorUtil
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class BoloCreationListener(val s3Service: AzureStorageService, val repository: BeOnTheLookOutRepository) {
    @Value("\${spring.cloud.azure.storage.blob.endpoint}")
    lateinit var endpoint: String

    //    @Async
//    @Transactional(Transactional.TxType.REQUIRES_NEW)
//    @TransactionalEventListener
    @EventListener
    fun createQrCodeListener(event: CreateQR) {
        val qrCodeImage = QRCodeGeneratorUtil.getQRCodeImage(event.url, 250, 250)
        s3Service.putObject(qrCodeImage, event.bucket, event.key)
        repository.findById(event.id).ifPresent {
            it.qrCodeKey = "$endpoint${event.bucket}/${event.key}"
            repository.save(it)
        }
    }
}