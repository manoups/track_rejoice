package one.breece.track_rejoice.domain

import jakarta.persistence.Embeddable

@Embeddable
class Photo(val bucket: String, val key: String)
