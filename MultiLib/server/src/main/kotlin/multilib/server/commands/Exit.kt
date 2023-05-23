package multilib.app.commands


import multilib.app.commands.tools.Result
import multilib.app.commands.tools.ArgsInfo
import multilib.app.commands.tools.SetMapForCommand
import multilib.server.uSender
import multilib.lib.list.MessageDto
import multilib.server.commands.Save


object Message {
    const val MESSAGE = "Происходит отключение от сервера..."
}

class Exit : Command {
    override val hidden: Boolean
        get() = true

    private val setMapForCommand = SetMapForCommand()
    private val printer = uSender
    private val argsInfo = ArgsInfo()
    override fun comply(variables: HashMap<String, Any>): Result {
        printer.print (MessageDto(emptyList(), Message.MESSAGE ))
        println("Происходит сохранение...")
        Save().comply(HashMap())
        return Result("Команда выполнена", false)
    }

    override fun getName(): String {
        return "exit"
    }
    override fun getDescription(): String {
        return "Выход из приложения. Коллекция автоматически не сохраняется. Передаваемых аргументов НЕТ."
    }

    override fun argContract(arguments : List<String>): HashMap<String, Any> {
        return HashMap()
    }
    override fun argsInfo(): HashMap<String, Int> {
        return argsInfo.setLimits(0,0,1)
    }

    override fun setMapForClient(): HashMap<String, String> {
        return setMapForCommand.setMapForCommand(0,0,false, Exit(), "")
    }
}