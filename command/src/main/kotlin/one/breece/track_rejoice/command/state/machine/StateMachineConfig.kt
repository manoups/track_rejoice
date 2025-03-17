package one.breece.track_rejoice.command.state.machine

import one.breece.track_rejoice.core.domain.BoloStates
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.statemachine.config.EnableStateMachineFactory
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer
import org.springframework.statemachine.listener.StateMachineListener
import org.springframework.statemachine.listener.StateMachineListenerAdapter
import org.springframework.statemachine.state.State
import java.util.*

@Configuration
@EnableStateMachineFactory
class StateMachineConfig : EnumStateMachineConfigurerAdapter<BoloStates, BoloEvents>() {

    override fun configure(config: StateMachineConfigurationConfigurer<BoloStates, BoloEvents>) {
        config
            .withConfiguration()
            .listener(listener());
    }

    override fun configure(states: StateMachineStateConfigurer<BoloStates, BoloEvents>) {
        states
            .withStates()
            .initial(BoloStates.DRAFT)
            .end(BoloStates.DELETED)
            .end(BoloStates.FOUND)
            .states(EnumSet.allOf(BoloStates::class.java))
    }

    override fun configure(transitions: StateMachineTransitionConfigurer<BoloStates, BoloEvents>) {
        transitions
            .withExternal()
            .source(BoloStates.DRAFT).target(BoloStates.ACTIVE).event(BoloEvents.PUBLISH)
            .and()
            .withExternal()
            .source(BoloStates.ACTIVE).target(BoloStates.FOUND).event(BoloEvents.MARK_FOUND)
            .and()
            .withExternal()
            .source(BoloStates.ACTIVE).target(BoloStates.DELETED).event(BoloEvents.DEACTIVATE)
    }

    @Bean
    fun listener(): StateMachineListener<BoloStates, BoloEvents> {
        return object : StateMachineListenerAdapter<BoloStates, BoloEvents>() {
            override fun stateChanged(from: State<BoloStates, BoloEvents>?, to: State<BoloStates, BoloEvents>) {
                println("State change ${from?.id} to ${to.id}")
            }
        }
    }
}