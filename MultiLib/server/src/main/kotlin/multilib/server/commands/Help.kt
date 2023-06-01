package multilib.server.commands


import multilib.server.commandManager
import multilib.server.commands.tools.ArgsInfo
import multilib.server.commands.tools.Result
import multilib.server.commands.tools.SetMapForCommand
import multilib.server.uSender
import multilib.lib.list.dto.MessageDto
import multilib.lib.list.dto.Types


class Help : Command {

    override val hidden: Boolean
        get() = true
    override val type: Types
        get() = Types.NO_SYNC

    private val argsInfo = ArgsInfo()
    private val setMapForCommand = SetMapForCommand()

    override fun comply(variables: HashMap<String, Any>): Result {
        var returnValue = ""
        val commandDescriptionList: HashMap<String, String> = commandManager.getCommandDescriptionList()
        for (command in commandDescriptionList) {
            returnValue += "${command.key}  ---  ${command.value}\n"
        }
        uSender.print(MessageDto(emptyList(), returnValue), emptyList())
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
        return setMapForCommand.setMapForCommand(0,0,true, Help(), "")
    }
}