package multilib.client



import multilib.client.commands.CommandList
import multilib.client.commands.Var
import multilib.client.commands.commandList
import multilib.client.handkers.Connect
import multilib.client.handkers.Scanner
import multilib.client.handkers.Messages
import multilib.client.handkers.Validator
import multilib.lib.list.MessageDto
import multilib.lib.list.Request
import multilib.lib.list.deserializeRequest
import multilib.lib.list.printers.UPrinter



class Manager {

    private val client = Client()
    val uPrinter = UPrinter()
    private val scanner = Scanner()
    private val validator = Validator()
    fun process(){
        val validator = Validator()
        val commands = CommandList()
        var running = true


        client sendMessage "commandList"


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

        while (running){

            val command = scanner.readLine()

            val commandAndArguments = command!!.split(" ")
            val name = commandAndArguments[0]
            var arguments = commandAndArguments.drop(1)


            if(validator checkCommand name){
                if (arguments.isNotEmpty() && arguments.last() == ""){
                    arguments = arguments.dropLast(1)
                }
                validator.manege(name, arguments)
            }
            else{
                uPrinter.print{ "Такой команды не существует. Узнать подробнее: help" }
            }
        }
    }

    fun continueManage(command : String, arguments : List<String>){
        if (arguments.isEmpty() && !commandList[command]!![Var.description]!!.contains(Var.allFields)){

            client sendMessage command
            uPrinter.print { getMessage()}
        }else{
            if (validateArguments(command, arguments)){
                uPrinter.print { getMessage()}
            }
        }
    }
    private fun validateArguments(c: String, a : List<String> ): Boolean{
        val description = commandList[c]!![Var.description]!!
        if (description != "" && !description.contains("field") && !description.contains(Var.wayToFile)){
            val returnValue = validator.validateOneArgument(a[0], description).toString()
            return if (this badValue returnValue){
                uPrinter.print { returnValue }
                false
            }else{
                client sendMessage "$c $returnValue"
                true
            }
        }else if (!description.contains(Var.allFields)  && !description.contains(Var.wayToFile)){
            val returnValue = (validator validateMoreArg a).toString()
            return if (this badValue returnValue){
                uPrinter.print { returnValue }
                false
            }else{
                client sendMessage "$c $returnValue"
                true
            }
        }else if (description.contains(Var.allFields)  && !description.contains(Var.wayToFile)){
            val answer = validator allFields a
            return if (this badValue answer){
                uPrinter.print { answer }
                false
            }else{
                client sendMessage "$c $answer"
                true
            }
        }else{
            if ((validator workWithFile a).isNotEmpty()){
                client sendMessage (validator workWithFile a).toString()
            }
            return true
        }
    }
    private fun getMessage(): String {
        return try{
            val value = client.getMessage()
            if(value.message.message == Var.exit){
                client.stop()
            }
            value.message.message
        }catch (e : Exception){
            Connect().tryAgain()
            "Отсутствует подключение к серверу. Повторная попытка через 10 секунд."
        }
    }
    private infix fun badValue(returnValue : String): Boolean{
        return returnValue == Messages.errorType || returnValue == Messages.errorField || returnValue == Messages.errorValue
    }
    private infix fun checkMap(map : Request) : Boolean{
        return map.message.message == Var.errorServer || map.message.message == Var.errorEP
    }
    private infix fun checkData(list : List<HashMap<String, String>>): Boolean {
        return list.isNotEmpty()
    }
}