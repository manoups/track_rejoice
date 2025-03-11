package one.breece.track_rejoice.repository

import org.springframework.data.repository.NoRepositoryBean
import org.springframework.data.repository.PagingAndSortingRepository
import java.util.*


@NoRepositoryBean
interface ReadOnlyRepository<T, ID> : PagingAndSortingRepository<T, ID> {
    fun findById(id: ID): Optional<T>?
    fun findAll(): List<T>?
}