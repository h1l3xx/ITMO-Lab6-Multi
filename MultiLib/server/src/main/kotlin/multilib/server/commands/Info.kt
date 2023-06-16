package multilib.server.commands


import multilib.server.collection
import multilib.server.commands.tools.ArgsInfo
import multilib.server.commands.tools.Result
import multilib.server.commands.tools.SetMapForCommand
import multilib.lib.list.dto.Types
import multilib.server.uSender


class Info : Command {

    override val hidden: Boolean
        get() = true
    override val type: Types
        get() = Types.SYNC

    private val argsInfo = ArgsInfo()
    private val setMapForCommand = SetMapForCommand()
    override suspend  fun comply(variables: HashMap<String, Any>): Result {

        val collectionInfo = collection.getCollection()

        uSender.print ( "Дата инициализации: " + collection.getCreationTime().toString() +"; Количество элементов: " +  collectionInfo.size)

        return Result("Команда выполнена успешно.", false)
    }

    override fun getName(): String {
        return "info"
    }

    override fun getDescription(): String {
        return "Вывод информации о коллекции. Передаваемых аргументов НЕТ."
    }

    override fun argsInfo(): HashMap<String, Int> {
        return argsInfo.setLimits(0,0,1)
    }

    override fun argContract(arguments: List<String>): HashMap<String, Any> {
        return HashMap()
    }

    override fun setMapForClient(): HashMap<String, String> {
        return setMapForCommand.setMapForCommand(0,0,true, Info(), "")
    }

}