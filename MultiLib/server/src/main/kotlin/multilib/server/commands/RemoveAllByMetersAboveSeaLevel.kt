package multilib.app.commands

import multilib.app.city.arrayFreeId
import multilib.app.collection
import multilib.app.commands.tools.ArgsInfo
import multilib.app.commands.tools.Result
import multilib.app.commands.tools.SetMapForCommand


class RemoveAllByMetersAboveSeaLevel: Command {
    private val argsInfo = ArgsInfo()
    private val setMapForCommand = SetMapForCommand()
    override fun comply(variables: HashMap<String, Any>): Result {


        val iterator = collection.getCollection().iterator()
        while (iterator.hasNext()) {
            val iterCity = iterator.next()
            if (iterCity.getMetersAboveSeaLevel() == variables[Var.meters]) {
                arrayFreeId = if (arrayFreeId.isNotEmpty()){
                    arrayFreeId.clone() + iterCity.getId()!!
                } else{
                    arrayOf(iterCity.getId()!!)
                }
                iterator.remove()
            }
        }

        return Result("Удалены все города, с указанным значением.", true)
    }

    override fun getName(): String {
        return "remove_all_by_meters_above_sea_level"
    }

    override fun getDescription(): String {
        return "Удаляет из коллекции все элементы, значение поля metersAboveSeaLevel которого эквивалентно заданному. Один передаваемых аргумент."
    }

    override fun argsInfo(): HashMap<String, Int> {
        return argsInfo.setLimits(1,1,1)
    }

    override fun argContract(arguments: List<String>): HashMap<String, Any> {
        val arg : HashMap<String, Any> = HashMap()
        arg[Var.meters] = arguments[0].toLong()
        return arg
    }

    override fun setMapForClient(): HashMap<String, String> {
        return setMapForCommand.setMapForCommand(1,1,true, RemoveAllByMetersAboveSeaLevel(), Var.long)
    }
}