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
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.view
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@WebMvcTest
@MockitoBean(types = [BicycleService::class, BikeQueryService::class, ItemQueryService::class, PetQueryService::class,
    BoloQueryService::class, UtilService::class, BoloService::class, AWSController::class, ItemSearchController::class,
    PetFormController::class, PayPalController::class, UploadController::class, UploadRestController::class,
    AccountController::class, RegistrationCaptchaController::class, RegistrationController::class,
    RegistrationRestController::class])
class BicycleFormControllerTest {

    @Autowired
    lateinit var wac: WebApplicationContext

    var mockMvc: MockMvc? = null

    @BeforeEach
    fun setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
            .apply<DefaultMockMvcBuilder>(SecurityMockMvcConfigurers.springSecurity())
            .build()

    }

    @WithMockUser("spring")
    @Test
    fun getBicycleForm() {
        mockMvc!!.perform(get("/bolo/form/transport"))
            .andExpect(status().isOk)
            .andExpect(view().name("bikesearchform"))
    }
}