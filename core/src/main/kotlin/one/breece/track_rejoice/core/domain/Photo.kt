package one.breece.track_rejoice.core.domain

import jakarta.persistence.Embeddable

@Embeddable
class Photo(val bucket: String, val key: String)
