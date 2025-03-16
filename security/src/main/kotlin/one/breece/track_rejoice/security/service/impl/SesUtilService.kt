package one.breece.track_rejoice.security.service.impl

import aws.sdk.kotlin.services.ses.SesClient
import aws.sdk.kotlin.services.ses.model.Body
import aws.sdk.kotlin.services.ses.model.Content
import aws.sdk.kotlin.services.ses.model.Destination
import aws.sdk.kotlin.services.ses.model.Message
import aws.sdk.kotlin.services.ses.model.SendEmailRequest
import aws.sdk.kotlin.services.ses.model.SesException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import kotlin.system.exitProcess

@Service
class SesUtilService {
    @Value("\${aws.region}")
    lateinit var REGION: String

    suspend fun send(
        recipient: String,
        strValue: String?
    ) {
        val sesClient = SesClient { region = REGION }
        // The HTML body of the email.
        val bodyHTML = ("<html>" + "<head></head>" + "<body>" + "<h1>Amazon RDS Items!</h1>"
                + "<textarea>$strValue</textarea>" + "</body>" + "</html>")

        val destinationOb = Destination {
            toAddresses = listOf(recipient)
        }

        val contentOb = Content {
            data = bodyHTML
        }

        val subOb = Content {
            data = "Item Report"
        }

        val bodyOb= Body {
            html = contentOb
        }

        val msgOb = Message {
            subject = subOb
            body = bodyOb
        }

        val emailRequest = SendEmailRequest {
            destination = destinationOb
            message = msgOb
            source = "<Enter email>"
        }

        try {
            println("Attempting to send an email through Amazon SES using the AWS SDK for Kotlin...")
            sesClient.sendEmail(emailRequest)

        } catch (e: SesException) {
            println(e.message)
            sesClient.close()
            exitProcess(0)
        }
    }
}