package multilib.app

import multilib.server.commands.Command
import multilib.server.commands.tools.Values
import multilib.lib.list.dto.MessageDto
import multilib.server.uSender


object Messages {
    const val MESSAGE = "Некорректное количество передаваемых аргументов. Точнее можно узнать в команде help."
}

var commandList  : HashMap<String, Command> = HashMap()
class CommandManager {

    private var commandDescriptionList   : HashMap<String, String> = HashMap()

    fun manage(name : String, arguments : List<String>){
        if (checkArgumentsInfo(name, arguments)){
            val variables = commandList[name]!!.argContract(arguments)
            val result = commandList[name]!!.comply(variables)
            if (result.bool){
                uSender.print (MessageDto(emptyList(), result.message), result.getCommit())
            }
        }
    }


    fun register(vararg commands: Command) {
        for (command in commands) {
            commandList[command.getName()] = command
            commandDescriptionList[command.getName()] = command.getDescription()
        }
    }
    fun checkCommand(name: String): Boolean {
        return commandList[name] != null
    }


    fun getCommandDescriptionList(): HashMap<String, String> {
        return commandDescriptionList
    }
    private fun checkArgumentsInfo(name: String, arguments : List<String>): Boolean {
        val info = commandList[name]!!.argsInfo()
        val size = arguments.size
        if (size > info[Values.max]!! || size < info[Values.min]!!){
            uSender.print (MessageDto(emptyList(), Messages.MESSAGE), emptyList())
            return false}
        else if (size < info[Values.max]!! && size > info[Values.min]!! && info[Values.between] == 0) {
            uSender.print (MessageDto(emptyList(),  Messages.MESSAGE), emptyList())
            return false
        }else if ((info[Values.max] == info[Values.min]) && info[Values.max] != size){
            uSender.print (MessageDto(emptyList(), Messages.MESSAGE), emptyList())
            return false
        }
        return true
    }
}