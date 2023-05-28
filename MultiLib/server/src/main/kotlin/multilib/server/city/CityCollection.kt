package multilib.server.city


import java.time.LocalDateTime
import java.util.*

var arrayFreeId = emptyArray<Long>()


class CityCollection {

    private val collection = LinkedList<City>()
    private var creationTime: LocalDateTime? = LocalDateTime.now()
    fun getCollection() : LinkedList<City>{
        return collection
    }
    fun add(city : City) {
        collection.add(city)
    }

    fun getCreationTime(): LocalDateTime? = creationTime
}