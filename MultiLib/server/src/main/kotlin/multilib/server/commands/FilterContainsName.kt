package multilib.server.commands

import multilib.server.city.City
import multilib.server.collection
import multilib.server.commands.tools.Result
import multilib.server.commands.tools.ArgsInfo
import multilib.server.commands.tools.SetMapForCommand
import multilib.lib.list.dto.SyncDto
import multilib.lib.list.dto.Types


class FilterContainsName : Command {

    override val hidden: Boolean
        get() = true
    override val sync: SyncDto
        get() = SyncDto(Types.SYNC)
    private val argsInfo = ArgsInfo()
    private val setMapForCommand = SetMapForCommand()
    override fun comply(variables: HashMap<String, Any>): Result {

        val collectionInfo = collection.getCollection()
        var returnValue = ""
        if (collectionInfo.isNotEmpty()) {
            val it: Iterator<City> = collection.getCollection().iterator()
            while (it.hasNext()) {
                val city: City = it.next()
                if (city.getName()!!.contains(variables["Substring"].toString())) {
                    returnValue += " " + city.getName()!! + "\n"
                }
            }
        } else {
            returnValue += "Коллекция пуста\n"
        }

        return Result("$returnValue Выведены все города с указанной подстрокой в названии.", true)
    }

    override fun getName(): String {
        return "filter_contains_name"
    }

    override fun getDescription(): String {
        return "Вывод элементов коллекции, поле name которых содержит заданную подстроку. Диапазон аргументов: от 1 до 100."
    }

    override fun argsInfo(): HashMap<String, Int> {
        return argsInfo.setLimits(1,1,1)
    }

    override fun argContract(arguments: List<String>): HashMap<String, Any> {
        val name : HashMap<String, Any> = HashMap()
        name["Substring"] = arguments[0]
        return name
    }

    override fun setMapForClient(): HashMap<String, String> {
        return setMapForCommand.setMapForCommand(1, 1, true, FilterContainsName(), Var.str)
    }
}