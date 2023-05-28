package multilib.app


import multilib.server.commands.ExecuteScript
import multilib.lib.list.dto.MessageDto
import multilib.server.commandManager
import multilib.server.uSender
import java.util.*


var sc = Scanner(System.`in`)
class Operator {
    fun runCommand(command: String){
        if (command.contains(ExecuteScript().getName())){
            val exAndCom = command.split(", ")
            val com = exAndCom.drop(1)
            commandManager.manage(exAndCom[0], com)
        }
        val commandAndArguments = command.split(" ")
        val name = commandAndArguments[0]
        val arguments = commandAndArguments.drop(1)
        if (!name.contains(ExecuteScript().getName())) {


            if (commandManager.checkCommand(name)) {
                if (arguments.isNotEmpty() && arguments.last() == "") {
                    var argumentsWithoutLast = arguments.dropLast(1)
                    if (arguments.size == 12) {
                        val addArg = ArrayList<String>()
                        for (i in 0..10) {
                            addArg.add(arguments[i])
                        }
                        argumentsWithoutLast = addArg
                    }
                    commandManager.manage(name, argumentsWithoutLast)
                } else {
                    commandManager.manage(name, arguments)
                }
            } else {
                uSender.print(MessageDto(emptyList(), Messages.MESSAGE), emptyList())
            }
        }
    }
}