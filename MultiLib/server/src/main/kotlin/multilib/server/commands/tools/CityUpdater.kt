package multilib.server.commands.tools

import multilib.server.city.City
import multilib.server.city.arrayFreeId
import multilib.server.collection
import multilib.server.commands.Add


class CityUpdater {

    fun updateCity(city : City, arguments : HashMap<String, Any>) : Result {
        return fullUpdate(city, arguments)
    }
}

private fun fullUpdate(city: City, a: HashMap<String, Any>) : Result {
    val cl = collection.getCollection()
    if (arrayFreeId.isNotEmpty()) {
        arrayFreeId[arrayFreeId.lastIndex + 1] = cl[0].getId()!!
    } else {
        arrayFreeId = arrayOf(cl[0].getId()!!)
    }
    cl.remove(city)
    val arguments = ArrayList<String>()
    for (i in 1..11) {
        arguments.add(a[i.toString()].toString())
    }
    val smt = Add().argContract(arguments)
    return Add().comply(smt)

}
