package one.breece.track_rejoice.command.events

data class CreateQR(val url: String, val bucket: String, val key: String, val id: Long)
