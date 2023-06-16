package multilib.app


import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import multilib.lib.list.Request
import multilib.lib.list.dto.Act
import multilib.server.commands.ExecuteScript
import multilib.lib.list.dto.MessageDto
import multilib.lib.list.dto.Types
import multilib.server.collection
import multilib.server.collectionActor
import multilib.server.commandManager
import multilib.server.commands.tools.ActorDto
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
        else if (request.message.message == "let's synchronize!"){
            launch {
                collectionActor.send(
                    ActorDto(
                        Pair(Act.LOAD, mutableListOf())
                    )
                )
                println(collection.getCollection().toString())
            }
        }else{
            launch {
                synchronizer.synchronize(request)
                runCommand(request.message.message)
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