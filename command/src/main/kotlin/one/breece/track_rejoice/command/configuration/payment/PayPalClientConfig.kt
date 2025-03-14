package one.breece.track_rejoice.command.configuration.payment

import com.paypal.sdk.Environment
import com.paypal.sdk.PaypalServerSdkClient
import com.paypal.sdk.authentication.ClientCredentialsAuthModel
import com.paypal.sdk.logging.configuration.ApiLoggingConfiguration
import org.slf4j.event.Level
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class PayPalClientConfig {
    @Value("\${paypal.public}")
    lateinit var PAYPAL_CLIENT_ID: String
    @Value("\${paypal.private}")
    lateinit var PAYPAL_CLIENT_SECRET: String
    @Bean
    fun client(): PaypalServerSdkClient {
        val client = PaypalServerSdkClient.Builder()
            .loggingConfig { builder: ApiLoggingConfiguration.Builder ->
                builder
                    .level(Level.DEBUG)
                    .requestConfig {
                        it.body(true)
                    }
                    .responseConfig {
                        it.headers(true)
                    }
            }
            .httpClientConfig {
                it.timeout(0)
            }
            .clientCredentialsAuth(
                ClientCredentialsAuthModel.Builder(
                    PAYPAL_CLIENT_ID,
                    PAYPAL_CLIENT_SECRET
                )
                    .build()
            )
            .environment(Environment.SANDBOX)
            .build()
        return client
    }
}