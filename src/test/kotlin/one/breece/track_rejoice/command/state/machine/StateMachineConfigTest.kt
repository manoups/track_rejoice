package one.breece.track_rejoice.command.state.machine

import one.breece.track_rejoice.command.configuration.payment.PayPalClientConfig
import one.breece.track_rejoice.command.service.BicycleService
import one.breece.track_rejoice.command.service.BoloService
import one.breece.track_rejoice.command.web.controller.*
import one.breece.track_rejoice.core.domain.BoloStates
import one.breece.track_rejoice.query.domain.BeOnTheLookOut
import one.breece.track_rejoice.query.domain.Bicycle
import one.breece.track_rejoice.query.domain.Item
import one.breece.track_rejoice.query.domain.Pet
import one.breece.track_rejoice.query.repository.BeOnTheLookoutQueryRepository
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
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.messaging.support.MessageBuilder
import org.springframework.statemachine.config.StateMachineFactory
import org.springframework.test.context.bean.override.mockito.MockitoBean
import reactor.core.publisher.Mono
import java.util.*

@SpringBootTest
//@DataJpaTest
@MockitoBean(
 types = [BicycleService::class, BikeQueryService::class, ItemQueryService::class, PetQueryService::class,
  BeOnTheLookOut::class, Bicycle::class, Item::class, Pet::class, BeOnTheLookoutQueryRepository::class,
  BoloQueryService::class, UtilService::class, BoloService::class, AWSController::class, ItemFormController::class,
  PetFormController::class, PayPalController::class, UploadController::class, UploadRestController::class,
  AccountController::class, RegistrationCaptchaController::class, RegistrationController::class,
  RegistrationRestController::class, PayPalClientConfig::class])
class StateMachineConfigTest {
 @Autowired
 lateinit var factory: StateMachineFactory<BoloStates, BoloEvents>

 @Test
 fun testStateMachine() {
  val sm = factory.getStateMachine(UUID.randomUUID())
  sm.start()
  println(">>>> "+sm.state.toString())
  sm.sendEvent(Mono.just(MessageBuilder.withPayload(BoloEvents.PUBLISH).setHeader("bolo_id", 1).build())).subscribe()
  println(">>>> "+sm.state.toString())
  sm.sendEvent(BoloEvents.MARK_FOUND)
  println(">>>> "+sm.state)
 }
}