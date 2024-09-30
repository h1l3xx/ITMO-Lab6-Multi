package multilib.client



import multilib.client.commands.CommandList
import multilib.client.commands.Var
import multilib.client.commands.commandList
import multilib.client.handkers.Connect
import multilib.client.handkers.Scanner
import multilib.client.handkers.Messages
import multilib.client.handkers.Validator
import multilib.lib.list.Request
import multilib.lib.list.dto.Types
import multilib.lib.list.printers.UPrinter
import kotlin.concurrent.thread


class Manager {


    val uPrinter = UPrinter()
    private val scanner = Scanner()
    private val validator = Validator()
    private var running = true
    fun process(){
        val client = Client()
        val validator = Validator()
        val commands = CommandList()



        client.sendMessage("commandList", Types.NO_SYNC.toString())


        val map = client.getMessage()
        var data : List<HashMap<String, String>> = emptyList()
        if (this checkMap map){
            uPrinter.print { map.message.message }
        }else{
            data = map.message.commandList
        }
        if (data.isEmpty() && map.message.message == Var.errorServer){
            Connect().tryAgain()
        }
        running = if (data.isNotEmpty()){
            commands.setCommandList(data)
            commands.showCommands()
            true

        }else{
            false
        }
        this goPingEP client
        while (running){

            val command = scanner.readLine() ?: continue

            val commandAndArguments = command.split(" ")
            val name = commandAndArguments[0]
            var arguments = commandAndArguments.drop(1)


            if(validator checkCommand name){
                if (arguments.isNotEmpty() && arguments.last() == ""){
                    arguments = arguments.dropLast(1)
                }
                validator.manege(name, arguments, client)
            }
            else{
                uPrinter.print{ "Такой команды не существует. Узнать подробнее: help" }
            }
        }
    }

    fun continueManage(command : String, arguments : List<String>, client : Client){
        if (arguments.isEmpty() && !commandList[command]!![Var.description]!!.contains(Var.allFields)){

            client.sendMessage(command, commandList[command]!![Var.type].toString())
            uPrinter.print { getMessage(client)}
        }else{
            if (validateArguments(command, arguments, client)){
                uPrinter.print { getMessage(client)}
            }
        }
    }
    private fun validateArguments(c: String, a : List<String>, client : Client): Boolean{
        val description = commandList[c]!![Var.description]!!
        if (description != "" && !description.contains("field") && !description.contains(Var.wayToFile) && c!="auth"&&c!="sign_up"){
            val returnValue = validator.validateOneArgument(a[0], description).toString()
            return if (this badValue returnValue){
                uPrinter.print { returnValue }
                false
            }else{
                client.sendMessage("$c $returnValue", commandList[c]!![Var.type].toString())
                true
            }
        }else if (!description.contains(Var.allFields)  && !description.contains(Var.wayToFile)){

            val returnValue = (validator validateMoreArg a).toString()
            return if (this badValue returnValue){
                uPrinter.print { returnValue }
                false
            }else{
                client.sendMessage("$c $returnValue", commandList[c]!![Var.type].toString())
                true
            }
        }else if (description.contains(Var.allFields)  && !description.contains(Var.wayToFile)){
            val answer = validator allFields a
            return if (this badValue answer){
                uPrinter.print { answer }
                false
            }else{
                client.sendMessage("$c $answer", commandList[c]!![Var.type].toString())
                true
            }
        }else{
            if ((validator workWithFile a).isNotEmpty()){
                client.sendMessage((validator workWithFile a).toString(), commandList[c]!![Var.type].toString())
            }
            return true
        }
    }
    private fun getMessage(client: Client): String {
        return try{
            val value = client.getMessage()
            if(value.message.message == Var.exit){
                client.stop("")
            }
            value.message.message
        }catch (e : Exception){
            e.printStackTrace()
            "Отсутствует подключение к серверу. Повторная попытка через 10 секунд."
        }
    }
    private infix fun badValue(returnValue : String): Boolean{
        return returnValue == Messages.errorType || returnValue == Messages.errorField || returnValue == Messages.errorValue
    }
    private infix fun checkMap(map : Request) : Boolean {
        return map.message.message == Var.errorServer || map.message.message == Var.errorEP
    }
    private infix fun goPingEP(client: Client){
        thread {
            var run = connectToEP
            while (run) {
                Thread.sleep(60_000)
                client.sendMessage("ping from client", Types.NO_SYNC.toString())
                val answer = client.getMessage().message.message
                if (answer != "success"){
                    client.stop("Sorry, EP is dead")
                }
                run = connectToEP
            }
        }
    }
}