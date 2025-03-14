package one.breece.track_rejoice.query.service.impl


import one.breece.track_rejoice.core.domain.BoloStateEnum
import one.breece.track_rejoice.core.projections.BeOnTheLookOutProj
import one.breece.track_rejoice.core.util.GeometryUtil
import one.breece.track_rejoice.query.domain.Bicycle
import one.breece.track_rejoice.query.domain.Item
import one.breece.track_rejoice.query.domain.Pet
import one.breece.track_rejoice.query.repository.BeOnTheLookoutQueryRepository
import one.breece.track_rejoice.query.service.BoloQueryService
import org.springframework.core.convert.converter.Converter
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class BoloQueryServiceImpl(
    private val beOnTheLookOutQueryRepository: BeOnTheLookoutQueryRepository,
    private val petToProjRepository: Converter<Pet, BeOnTheLookOutProj>,
    private val itemToProjRepository: Converter<Item, BeOnTheLookOutProj>,
    private val bicycleToProjRepository: Converter<Bicycle, BeOnTheLookOutProj>,
) : BoloQueryService {

    override fun findAllByLngLat(
        lng: Double,
        lat: Double,
        distanceInMeters: Double,
        pageable: Pageable
    ): Page<BeOnTheLookOutProj> {
        val point = GeometryUtil.parseLocation(lng, lat)
        val bolos = beOnTheLookOutQueryRepository.findIdsByLngLat(point, distanceInMeters, BoloStateEnum.ACTIVE, pageable)
//        TODO: Delegate to factory pattern
        val responsePayload = bolos.content.map {
            when(it) {
                is Pet -> petToProjRepository.convert(it)
                is Item -> itemToProjRepository.convert(it)
                is Bicycle -> bicycleToProjRepository.convert(it)
                else -> throw IllegalArgumentException("Unknown type $it")
            }
        }
        return PageImpl(responsePayload, pageable, bolos.totalElements)
    }
}