package multilib.app


import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import multilib.lib.list.Request
import multilib.lib.list.dto.CommitDto
import multilib.server.commands.ExecuteScript
import multilib.lib.list.dto.MessageDto
import multilib.lib.list.dto.Types
import multilib.server.commandManager
import multilib.server.commands.Command
import multilib.server.database.Synchronizer
import multilib.server.uSender
import java.util.*


var sc = Scanner(System.`in`)
class Operator {
    private val synchronizer = Synchronizer()
    private val scope = CoroutineScope(Job())

    fun checkSync(request : Request) = scope.launch{
        if (request.type != null && request.type != Types.SYNC && request.message.message != "let's synchronize!"){
            launch {
                runCommand(request.message.message)
            }
        }
        else if (request.list.isEmpty()){
            launch {
                uSender.print(MessageDto(emptyList(), "You can continue"), emptyList())
                runCommand(request.message.message)
            }
        }
        else if (request.message.message == "let's synchronize!"){
            launch {
                if (request.list.isNotEmpty()){
                    synchronizer.synchronize(request, false)
                }
            }
        }else{
            launch {
                if (request.list.isNotEmpty()){
                    synchronizer.synchronize(request, true)
                    runCommand(request.message.message)
                }else{
                    runCommand(request.message.message)
                }
            }
        }
    }
    fun runCommand(command: String) = CoroutineScope(scope.coroutineContext + Job()).launch{
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
                    launch {
                        commandManager.manage(name, argumentsWithoutLast)
                    }
                } else {
                    launch {
                        commandManager.manage(name, arguments)
                    }
                }
            } else {
                launch {
                    uSender.print(MessageDto(emptyList(), Messages.MESSAGE), emptyList())
                }
            }
        }
    }
}