package one.breece.track_rejoice.web.controller

import one.breece.track_rejoice.command.service.BicycleService
import one.breece.track_rejoice.command.service.BoloService
import one.breece.track_rejoice.command.web.controller.*
import one.breece.track_rejoice.configuration.SecurityConfig
import one.breece.track_rejoice.query.service.BikeQueryService
import one.breece.track_rejoice.query.service.BoloQueryService
import one.breece.track_rejoice.query.service.ItemQueryService
import one.breece.track_rejoice.query.service.PetQueryService
import one.breece.track_rejoice.security.service.UtilService
import one.breece.track_rejoice.security.web.controller.AccountController
import one.breece.track_rejoice.security.web.controller.RegistrationCaptchaController
import one.breece.track_rejoice.security.web.controller.RegistrationController
import one.breece.track_rejoice.security.web.controller.RegistrationRestController
import org.junit.jupiter.api.Test
import org.springframework.boot.autoconfigure.security.servlet.RequestMatcherProvider
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.security.web.authentication.RememberMeServices
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.view


@WebMvcTest
@MockitoBean(
    types = [BicycleService::class, BikeQueryService::class, ItemQueryService::class, PetQueryService::class,
        BoloQueryService::class, UtilService::class, BoloService::class, AWSController::class, ItemFormController::class,
        PetFormController::class, PayPalController::class, UploadController::class, UploadRestController::class,
        AccountController::class, RegistrationCaptchaController::class, RegistrationController::class,
        RegistrationRestController::class, AuthenticationFailureHandler::class, RequestMatcherProvider::class, RememberMeServices::class]
)
@Import(SecurityConfig::class)
class IndexController:BaseIT() {
    @Test
    fun testGetIndex() {
        mockMvc!!.perform(get("/"))
            .andExpect(status().isOk)
            .andExpect(view().name("index"))
    }

    @Test
    fun testGetIndexWithAnonymous() {
        mockMvc!!.perform(get("/").with(anonymous()))
            .andExpect(status().isOk)
            .andExpect(view().name("index"))
    }
}