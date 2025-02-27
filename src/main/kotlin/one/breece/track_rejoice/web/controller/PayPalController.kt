package one.breece.track_rejoice.web.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.paypal.sdk.PaypalServerSdkClient
import com.paypal.sdk.exceptions.ApiException
import com.paypal.sdk.models.*
import one.breece.track_rejoice.service.PetService
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import java.io.IOException

@Controller
@ConditionalOnProperty(name = ["payments"], havingValue = "enabled")
class PayPalController(
    private val objectMapper: ObjectMapper,
    private val client: PaypalServerSdkClient,
    private val petService: PetService
) {
    @PostMapping("/api/orders")
    fun createOrder(@RequestBody request: Map<String, Any>): ResponseEntity<Order> {
        try {
            val cart = objectMapper.writeValueAsString(request["cart"])
            val response = createOrder(cart)
            return ResponseEntity(response, HttpStatus.OK);
        } catch (e: Exception) {
            e.printStackTrace();
            return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Throws(IOException::class, ApiException::class)
    fun createOrder(cart: String): Order {
        val ordersCreateInput = OrdersCreateInput.Builder(
            null,
            OrderRequest.Builder(
                CheckoutPaymentIntent.fromString("CAPTURE"),
                listOf(
                    PurchaseUnitRequest.Builder(
                        AmountWithBreakdown.Builder(
                            "EUR",
                            "5"
                        ).build()
                    ).build()
                )
            ).build()
        ).build()
        val ordersController = client.ordersController
        val apiResponse = ordersController.ordersCreate(ordersCreateInput);
        return apiResponse.result
    }

    @PostMapping("/api/orders/{orderID}/{announcementId}/capture")
    fun captureOrder(@PathVariable orderID: String, @PathVariable announcementId: Long): ResponseEntity<Order> {
        try {
            petService.enableAnnouncement(announcementId)
            val response = captureOrders(orderID);
            return ResponseEntity(response, HttpStatus.OK)
        } catch (e: Exception) {
            e.printStackTrace()
            return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @Throws(IOException::class, ApiException::class)
    fun captureOrders(orderID: String): Order {
        val ordersCaptureInput = OrdersCaptureInput.Builder(
            orderID,
            null
        ).build()
        val ordersController = client.ordersController
        val apiResponse = ordersController.ordersCapture(ordersCaptureInput)
        return apiResponse.result
    }

}