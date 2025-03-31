package one.breece.track_rejoice.command.dto

import one.breece.track_rejoice.core.command.PhotoDescriptor

data class PhotoResponse(val name: String, val photoDescriptor: PhotoDescriptor,val redirect: String)
