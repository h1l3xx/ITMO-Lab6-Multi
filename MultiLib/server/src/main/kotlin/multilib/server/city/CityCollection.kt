package multilib.server.city


import java.time.LocalDateTime
import java.util.*



class CityCollection {

    private val collection = LinkedList<City>()
    private var creationTime: LocalDateTime? = LocalDateTime.now()
    fun getCollection() : LinkedList<City>{
        return collection
    }
    fun remove(city: City){
        collection.remove(city)
    }
    fun add(city : City) {
        collection.add(city)
    }
    fun getCreationTime(): LocalDateTime? = creationTime
}