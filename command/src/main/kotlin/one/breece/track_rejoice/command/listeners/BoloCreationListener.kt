package one.breece.track_rejoice.command.listeners

import jakarta.transaction.Transactional
import kotlinx.coroutines.runBlocking
import one.breece.track_rejoice.command.events.CreateQR
import one.breece.track_rejoice.command.repository.BeOnTheLookOutRepository
import one.breece.track_rejoice.command.service.impl.QRCodeGeneratorUtil
import one.breece.track_rejoice.command.service.impl.S3Service
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.event.TransactionalEventListener

@Component
class BoloCreationListener(val s3Service: S3Service, val repository: BeOnTheLookOutRepository) {
//    @Async
//    @Transactional(Transactional.TxType.REQUIRES_NEW)
//    @TransactionalEventListener
    @EventListener
    fun createQrCodeListener(event: CreateQR) {
        runBlocking {
            val qrCodeImage = QRCodeGeneratorUtil.getQRCodeImage(event.url, 250, 250)
            s3Service.putObject(qrCodeImage, event.bucket, event.key)
            repository.findById(event.id).ifPresent {
                it.qrCodeKey = "https://${event.bucket}.s3.amazonaws.com/${event.key}"
                repository.save(it)
            }
        }
    }
}