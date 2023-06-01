package multilib.app.commands



import multilib.server.city.CityComparator
import multilib.server.collection
import multilib.server.commands.tools.ArgsInfo
import multilib.server.commands.tools.Result
import multilib.server.commands.tools.SetMapForCommand
import multilib.lib.list.dto.Types
import multilib.server.commands.Command


class Sort : Command {
    override val type: Types
        get() = Types.SYNC
    override val hidden: Boolean
        get() = true
    private val argsInfo = ArgsInfo()
    private val setMapForCommand = SetMapForCommand()
    override fun comply(variables: HashMap<String, Any>): Result {

        val c = CityComparator()
        val cl = collection.getCollection()

        cl.sortWith(c)

        return Result("Коллекция отсортирована", true)
    }

    override fun setMapForClient(): HashMap<String, String> {
        return setMapForCommand.setMapForCommand(0,0,true, Sort(), "")
    }

    override fun getName(): String {
        return "sort"
    }

    override fun getDescription(): String {
        return "Сортировка элементов в естественном порядке. Передаваемых аргументов НЕТ."
    }

    override fun argsInfo(): HashMap<String, Int> {
        return argsInfo.setLimits(0,0,1)
    }

    override fun argContract(arguments: List<String>): HashMap<String, Any> {
        return HashMap()
    }
}