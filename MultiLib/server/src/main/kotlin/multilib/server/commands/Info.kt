package multilib.app.commands


import multilib.server.collection
import multilib.app.commands.tools.ArgsInfo
import multilib.app.commands.tools.Result
import multilib.app.commands.tools.SetMapForCommand
import multilib.server.uSender


class Info : Command {

    override val hidden: Boolean
        get() = true

    private val argsInfo = ArgsInfo()
    private val setMapForCommand = SetMapForCommand()
    override fun comply(variables: HashMap<String, Any>): Result {

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