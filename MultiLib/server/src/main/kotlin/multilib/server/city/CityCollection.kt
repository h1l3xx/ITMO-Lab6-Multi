package multilib.app.city


import java.time.LocalDateTime
import java.util.*

var arrayFreeId = emptyArray<Long>()


class CityCollection {

    private val collection = LinkedList<City>()
    private var creationTime: LocalDateTime? = LocalDateTime.now()
    private var maxId : Long = 0
    fun getCollection() : LinkedList<City>{
        return collection
    }
    fun add(city : City) {
        collection.add(city)
    }

    fun getCreationTime(): LocalDateTime? = creationTime
    fun setCreationTime(creationTime: LocalDateTime? ) {
        this.creationTime = creationTime
    }fun getFreeId() : Long{
        return if (arrayFreeId.isNotEmpty()){
            val id = arrayFreeId[0]
            arrayFreeId = removeFirstItem(arrayFreeId)
            id
        }else{
            if (collection.isNotEmpty()){
                maxId = collection.size.toLong()+1
                maxId
            }else{
                maxId += 1
                maxId
            }
        }
    }
    private fun removeFirstItem(array: Array<Long>): Array<Long> {
        return array.copyOfRange(1, array.size)
    }
}