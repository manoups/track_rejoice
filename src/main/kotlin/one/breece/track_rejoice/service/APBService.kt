package one.breece.track_rejoice.service

interface APBService<in APBCommand, out APBResponse> {
    fun createAPB(announcementCommand: APBCommand): APBResponse
    fun deleteById(id: Long)
    fun readById(id: Long): APBResponse?
}
