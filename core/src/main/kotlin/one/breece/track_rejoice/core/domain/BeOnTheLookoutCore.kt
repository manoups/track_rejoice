package one.breece.track_rejoice.core.domain

import jakarta.annotation.Nonnull
import jakarta.persistence.Column
import jakarta.persistence.ElementCollection
import jakarta.persistence.MappedSuperclass
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import org.apache.commons.io.FilenameUtils
import org.locationtech.jts.geom.Geometry
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.HashSet

@MappedSuperclass
open class BeOnTheLookoutCore(
    @Pattern(regexp = "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$")
    var phoneNumber: String,
    @Nonnull
    var lastSeenDate: LocalDateTime = LocalDateTime.now(),


    @Column(columnDefinition = "geometry(Geometry, 4326)", nullable = false)
    open val lastSeenLocation: Geometry,
    @Column(columnDefinition = "varchar(1024)")
    var extraInfo: String? = null,
    @Column(unique = true, nullable = false, updatable = false)
    val sku: UUID = UUID.randomUUID(),
    @NotNull
    @Column(columnDefinition = "boolean default false")
    var enabled: Boolean = false,
    @ElementCollection
    var photo: MutableSet<Photo> = HashSet()
) : BoilerPlate() {
    fun addPhoto(bucketName: String, name: String) {
        photo.add(Photo(bucketName, name))
    }

    fun removePhoto(key: String): Photo? {
        val first = findPhoto(key)
        if (first != null) {
            photo.remove(first)
            return first
        }
        return null
    }

    fun findPhoto(key: String): Photo? {
        return photo.firstOrNull { FilenameUtils.getName(it.key) == key }
    }
}