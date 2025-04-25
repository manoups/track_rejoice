package one.breece.track_rejoice.web.controller

import one.breece.track_rejoice.command.service.BicycleService
import one.breece.track_rejoice.command.service.BoloService
import one.breece.track_rejoice.command.web.controller.*
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
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest
@MockitoBean(
    types = [BicycleService::class, BikeQueryService::class, ItemQueryService::class, PetQueryService::class,
        BoloQueryService::class, UtilService::class, BoloService::class, AWSController::class, ItemFormController::class,
        PetFormController::class, PayPalController::class, UploadController::class, UploadRestController::class,
        AccountController::class, RegistrationCaptchaController::class, RegistrationController::class,
        RegistrationRestController::class]
)
class BicycleFormControllerTest : BaseIT() {

    @WithMockUser("spring")
    @Test
    fun givenSecureEndpoint_whenForcedCredentials_then200() {
        mockMvc!!.perform(get("/bolo/form/transport"))
            .andExpect(status().isOk)
            .andExpect(view().name("bikesearchform"))
            .andExpect(model().attributeExists("bicycleAnnouncementCommand"))
    }

    @Test
    fun givenSecureEndpoint_whenUnauthorizedUser_then401() {
        mockMvc!!.perform(get("/bolo/form/transport").with(httpBasic("foo", "bar")))
            .andExpect(status().isUnauthorized)
    }

    //    User created in properties
    @Test
    fun givenSecureEndpoint_whenValidUser_then200() {
        mockMvc!!.perform(get("/bolo/form/transport").with(httpBasic(username, password)))
            .andExpect(status().isOk)
            .andExpect(view().name("bikesearchform"))
            .andExpect(model().attributeExists("bicycleAnnouncementCommand"))
    }
}