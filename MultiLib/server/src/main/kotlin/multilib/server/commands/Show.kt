package multilib.app.commands


import multilib.app.collection
import multilib.app.commands.tools.ArgsInfo
import multilib.app.commands.tools.Result
import multilib.app.commands.tools.SetMapForCommand
import multilib.app.uSender
import multilib.lib.list.MessageDto


class Show : Command {
    private val argsInfo = ArgsInfo()
    private val setMapForCommand = SetMapForCommand()
    override fun comply(variables: HashMap<String, Any>): Result {

        val collection = collection.getCollection()
        var sendValue = ""
        if (collection.size > 0) {
            for (c in collection) {
                sendValue += c.toString() + "\n"
            }
            uSender.print(MessageDto(emptyList(), sendValue))

        } else {
            sendValue = "Коллекция пуста"
            uSender.print (MessageDto(emptyList(), sendValue))
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