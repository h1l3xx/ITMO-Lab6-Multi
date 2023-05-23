package multilib.app.commands


import multilib.server.commandManager
import multilib.app.commands.tools.ArgsInfo
import multilib.app.commands.tools.Result
import multilib.app.commands.tools.SetMapForCommand
import multilib.server.uSender
import multilib.lib.list.MessageDto


class Help : Command{

    override val hidden: Boolean
        get() = true

    private val argsInfo = ArgsInfo()
    private val setMapForCommand = SetMapForCommand()

    override fun comply(variables: HashMap<String, Any>): Result {
        var returnValue = ""
        val commandDescriptionList: HashMap<String, String> = commandManager.getCommandDescriptionList()
        for (command in commandDescriptionList) {
            returnValue += "${command.key}  ---  ${command.value}\n"
        }
        uSender.print(MessageDto(emptyList(), returnValue))
        return Result("Команда успешно выполнена", false)
    }

    override fun getDescription(): String {
        return "Справка по командам. Передаваемых аргументов НЕТ."
    }

    override fun argsInfo(): HashMap<String, Int> {
        return argsInfo.setLimits(0,0,1)
    }

    override fun getName(): String {
        return "help"
    }

    override fun argContract(arguments: List<String>): HashMap<String, Any> {
        return HashMap()
    }

    override fun setMapForClient(): HashMap<String, String> {
        return setMapForCommand.setMapForCommand(0,0,true,Help(), "")
    }
}