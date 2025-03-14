package one.breece.track_rejoice

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.modulith.core.ApplicationModules
import org.springframework.modulith.docs.Documenter

@SpringBootTest
class TrackRejoiceApplicationTests {

    @Test
    fun verifiesModularStructure() {
        val modules: ApplicationModules = ApplicationModules.of(TrackRejoiceApplication::class.java)
        modules.verify()
    }

    @Test
    fun createModuleDocumentation() {
        val modules: ApplicationModules = ApplicationModules.of(TrackRejoiceApplication::class.java)
        Documenter(modules)
            .writeDocumentation()
            .writeIndividualModulesAsPlantUml()
    }

}
