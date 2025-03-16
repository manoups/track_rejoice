package one.breece.track_rejoice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync

@EnableAsync
@SpringBootApplication
class TrackRejoiceApplication

fun main(args: Array<String>) {
    runApplication<TrackRejoiceApplication>(*args)
}
