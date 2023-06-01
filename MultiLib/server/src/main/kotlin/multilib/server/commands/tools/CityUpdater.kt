package multilib.server.commands.tools

import multilib.server.city.City
import multilib.server.city.CityCreator
import multilib.server.collection
import java.time.ZonedDateTime


class CityUpdater {

    fun updateCity(city : City, arguments : HashMap<String, String>) : Result {
        return fullUpdate(city, arguments)
    }
}

private fun fullUpdate(city: City, a: HashMap<String, String>) : Result {
    val cl = collection.getCollection()
    cl.remove(city)
    val arguments = mutableListOf<String>()
    arguments.add(city.getId().toString())
    for (i in 1..11) {
        arguments.add(a[i.toString()].toString())
    }
    println(arguments)
    val cityCreator = CityCreator()
    val commit = cityCreator.create(city.getOwner(), city.getCreationDate()!!, arguments[0].toLong(), arguments[1],
        arguments[2].toLong(), arguments[3].toFloat(), arguments[4].toInt(), arguments[5].toLong(), arguments[6].toLong(),
        arguments[7].toDouble(), arguments[8], arguments[9], ZonedDateTime.parse(arguments[11]), arguments[10].toInt())

    return Result("Поля города изменены", true, listOf(commit))
}
