package one.breece.track_rejoice.mapper


import one.breece.track_rejoice.domain.command.Trace
import one.breece.track_rejoice.web.dto.TraceDTO
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

@Component
class TraceMapper : Converter<Trace, TraceDTO> {
    override fun convert(source: Trace): TraceDTO {
        return TraceDTO(source.location, source.date!!)
    }

}
