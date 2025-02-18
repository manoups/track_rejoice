package one.breece.track_rejoice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

@SpringBootApplication
class TrackRejoiceApplication

fun main(args: Array<String>) {
    runApplication<TrackRejoiceApplication>(*args)
}
