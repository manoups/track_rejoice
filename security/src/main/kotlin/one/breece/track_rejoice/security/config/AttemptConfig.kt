package one.breece.track_rejoice.security.config

import com.google.common.cache.CacheBuilder
import com.google.common.cache.CacheLoader
import com.google.common.cache.LoadingCache
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.TimeUnit

@Configuration
class AttemptConfig {
    companion object {
        const val MAX_ATTEMPT = 3
    }
    @Bean
    fun inMemoryCache(): LoadingCache<String, Int> =
        CacheBuilder.newBuilder().expireAfterWrite(24, TimeUnit.HOURS).build(object : CacheLoader<String, Int>() {
            override fun load(key: String): Int {
                return 0
            }
        })
}