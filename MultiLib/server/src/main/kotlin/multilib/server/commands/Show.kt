package multilib.app.commands


import multilib.server.collection
import multilib.server.commands.tools.ArgsInfo
import multilib.server.commands.tools.Result
import multilib.server.commands.tools.SetMapForCommand
import multilib.server.uSender
import multilib.lib.list.dto.MessageDto
import multilib.lib.list.dto.SyncDto
import multilib.lib.list.dto.Types
import multilib.server.commands.Command


class Show : Command {
    override val sync: SyncDto
        get() = SyncDto(Types.SYNC)
    override val hidden: Boolean
        get() = true
    private val argsInfo = ArgsInfo()
    private val setMapForCommand = SetMapForCommand()
    override fun comply(variables: HashMap<String, Any>): Result {

        val collection = collection.getCollection()
        var sendValue = ""
        if (collection.size > 0) {
            for (c in collection) {
                sendValue += c.toString() + "\n"
            }
            uSender.print(MessageDto(emptyList(), sendValue), emptyList())

        } else {
            sendValue = "Коллекция пуста"
            uSender.print (MessageDto(emptyList(), sendValue), emptyList())
        }

        return Result("Команда выполнена успешно.", false)
    }

    override fun getName(): String {
        return "show"
    }

    override fun getDescription(): String {
        return "Вывод элементов коллекции. Передаваемых аргументов НЕТ."
    }

    override fun setMapForClient(): HashMap<String, String> {
        return setMapForCommand.setMapForCommand(0,0,true, Show(), "")
    }

    override fun argContract(arguments: List<String>): HashMap<String, Any> {
        return HashMap()
    }

    override fun argsInfo(): HashMap<String, Int> {
        return argsInfo.setLimits(0,0,1)
    }
}