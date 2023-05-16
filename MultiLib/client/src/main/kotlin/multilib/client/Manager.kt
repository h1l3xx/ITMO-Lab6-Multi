package multilib.client



import multilib.list.Deserialization
import multilib.client.commands.CommandList
import multilib.client.commands.Var
import multilib.client.commands.commandList
import multilib.client.handkers.Connect
import multilib.client.handkers.Scanner
import multilib.client.handkers.Messages
import multilib.client.handkers.Validator
import multilib.lib.list.deserializeRequest
import multilib.lib.list.printers.UPrinter

import kotlin.system.exitProcess


class Manager {

    private val client = Client()
    val uPrinter = UPrinter()
    private val scanner = Scanner()
    val deserialization = Deserialization()
    fun process(){
        val validator = Validator()
        val commands = CommandList()
        var running = true


        client.sendMessage("commandList")

        val map = deserializeRequest(client.getMessage())

        val data = map.message.commandList

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


            if(validator.checkCommand(name)){
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
            //val mapForSand = MapBuilder().buildMap(command)
            client.sendMessage(command)
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
            val returnValue = Validator().validateOneArgument(a[0], description).toString()
            return if (badValue(returnValue)){
                uPrinter.print { returnValue }
                false
            }else{
                client.sendMessage("$c $returnValue")
                true
            }
        }else if (!description.contains(Var.allFields)  && !description.contains(Var.wayToFile)){
            val returnValue = Validator().validateMoreArg(a).toString()
            return if (badValue(returnValue)){
                uPrinter.print { returnValue }
                false
            }else{
                client.sendMessage("$c $returnValue")
                true
            }
        }else if (description.contains(Var.allFields)  && !description.contains(Var.wayToFile)){
            val answer = Validator().allFields(a)
            return if (badValue(answer)){
                uPrinter.print { answer }
                false
            }else{
                client.sendMessage("$c $answer")
                true
            }
        }else{
            if (Validator().workWithFile(a).isNotEmpty()){
                client.sendMessage(Validator().workWithFile(a).toString())
            }
            return true
        }
    }
    private fun getMessage(): String {
        return try{
            val value = deserializeRequest(client.getMessage())
            if(value.message.message == Var.exit){
                exitProcess(1)
            }
            return value.message.message
        }catch (e : Exception){
            Connect().tryAgain()
            return "Отсутствует подключение к серверу. Повторная попытка через 10 секунд."
        }
    }
    private fun badValue(returnValue : String): Boolean{
        return returnValue == Messages.errorType || returnValue == Messages.errorField || returnValue == Messages.errorValue
    }
}