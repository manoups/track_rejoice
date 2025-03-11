package one.breece.track_rejoice.domain

import jakarta.annotation.Nonnull
import jakarta.persistence.Column
import jakarta.persistence.MappedSuperclass
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import one.breece.track_rejoice.domain.command.BoilerPlate
import org.locationtech.jts.geom.Geometry
import java.time.LocalDateTime
import java.util.*

@MappedSuperclass
class BeOnTheLookoutCore(@Pattern(regexp = "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$")
                          var phoneNumber: String,
                         @Nonnull
                          var lastSeenDate: LocalDateTime = LocalDateTime.now(),


                         @Column(columnDefinition = "geometry(Geometry, 4326)", nullable = false)
                          open val lastSeenLocation: Geometry,
                         @Column(columnDefinition =  "varchar(1024)")
                          var extraInfo: String? = null,
                         @Column(unique = true, nullable=false, updatable = false)
                          val sku: UUID = UUID.randomUUID(),
                         @NotNull
                          @Column(columnDefinition = "boolean default false")
                          var enabled: Boolean = false
): BoilerPlate()