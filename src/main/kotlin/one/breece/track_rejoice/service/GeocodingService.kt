package one.breece.track_rejoice.service

import one.breece.track_rejoice.commands.AddressCommand
import org.locationtech.jts.geom.Point


interface GeocodingService {
    fun geocode(addressCommand: AddressCommand): Point?
    fun reverseGeocode(lat: Double, lon: Double): AddressCommand?
}
