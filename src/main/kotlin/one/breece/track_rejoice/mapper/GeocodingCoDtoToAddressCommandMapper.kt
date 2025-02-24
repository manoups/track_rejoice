package one.breece.track_rejoice.mapper

import one.breece.track_rejoice.commands.AddressCommand
import one.breece.track_rejoice.web.dto.AddressDTO
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

@Component
class GeocodingCoDtoToAddressCommandMapper: Converter<AddressDTO, AddressCommand> {
    override fun convert(source: AddressDTO): AddressCommand {
        return AddressCommand(source.road, source.houseNumber, source.postcode, source.town, source.country)
    }
}