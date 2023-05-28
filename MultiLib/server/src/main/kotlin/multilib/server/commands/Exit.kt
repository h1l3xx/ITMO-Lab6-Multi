package multilib.server.commands


import multilib.server.commands.tools.Result
import multilib.server.commands.tools.ArgsInfo
import multilib.server.commands.tools.SetMapForCommand
import multilib.server.uSender
import multilib.lib.list.dto.MessageDto
import multilib.lib.list.dto.SyncDto
import multilib.lib.list.dto.Types


object Message {
    const val MESSAGE = "Происходит отключение от сервера..."
}

class Exit : Command {
    override val hidden: Boolean
        get() = true
    override val sync: SyncDto
        get() = SyncDto(Types.NO_SYNC)

    private val setMapForCommand = SetMapForCommand()
    private val printer = uSender
    private val argsInfo = ArgsInfo()
    override fun comply(variables: HashMap<String, Any>): Result {
        printer.print (MessageDto(emptyList(), Message.MESSAGE), emptyList())
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