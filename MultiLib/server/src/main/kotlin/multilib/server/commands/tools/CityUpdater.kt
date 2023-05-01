package multilib.app.commands.tools

import multilib.app.city.City
import multilib.app.city.arrayFreeId
import multilib.app.collection
import multilib.app.commands.Add

object Numbers {
    const val one = "1"
    const val two = "2"
    const val three = "3"
    const val four = "4"
    const val five = "5"
    const val six = "6"
    const val seven = "7"
    const val eight = "8"
    const val nine ="9"
    const val ten = "10"
    const val eleven = "11"
    const val twelve = "12"
}

class CityUpdater {

    private var varsShaper = VarsShaper()

    fun updateCity(city : City, arguments : HashMap<String, Any>) {
        fullUpdate(city, arguments)
    }
}
private fun fullUpdate(city : City, a: HashMap<String, Any>){
    val cl = collection.getCollection()
    if (arrayFreeId.isNotEmpty()){
        arrayFreeId[arrayFreeId.lastIndex+1] = cl[0].getId()!!}
    else{
        arrayFreeId =  arrayOf(cl[0].getId()!!)
    }
    cl.remove(city)
    val arguments = ArrayList<String>()
    for (i in 1..11){
        arguments.add(a[i.toString()].toString())
    }
    val smt = Add().argContract(arguments)
    Add().comply(smt)

}
