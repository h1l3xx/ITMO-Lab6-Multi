package multilib.server.commands.tools

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import multilib.lib.list.dto.CommitDto
import multilib.lib.list.dto.CommitType
import multilib.server.city.City
import multilib.server.city.CityCreator
import multilib.server.collection
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter


class CityUpdater {

    suspend fun updateCity(city : City, arguments : HashMap<String, String>) : Result {
        return fullUpdate(city, arguments)
    }
}

private suspend fun fullUpdate(city: City, a: HashMap<String, String>) : Result {
    val scope = CoroutineScope(Job())
    val cl = collection.getCollection()
    cl.remove(city)
    val arguments = mutableListOf<String>()
    arguments.add(city.getId().toString())
    for (i in 1..11) {
        arguments.add(a[i.toString()].toString())
    }
    println(arguments)
    val cityCreator = CityCreator()
    val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val date: LocalDate = LocalDate.parse(arguments[11], formatter)
    val birt: ZonedDateTime = date.atStartOfDay(ZoneId.systemDefault())
    var commit : CommitDto? = null
    scope.launch {
        commit = cityCreator.create(city.getOwner(), city.getCreationDate()!!, arguments[0].toLong(), arguments[1],
        arguments[2].toLong(), arguments[3].toFloat(), arguments[4].toInt(), arguments[5].toLong(), arguments[6].toLong(),
        arguments[7].toDouble(), arguments[8], arguments[9], birt, arguments[10].toInt())
        commit!!.type = CommitType.UPDATE
    }.join()


    return Result("Поля города изменены", true, listOf(commit!!))
}
