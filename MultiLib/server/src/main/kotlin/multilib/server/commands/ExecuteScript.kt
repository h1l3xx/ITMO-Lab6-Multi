package multilib.server.commands


import multilib.server.commands.tools.ArgsInfo

import multilib.server.commands.tools.Result
import multilib.server.commands.tools.SetMapForCommand
import multilib.server.operator
import multilib.server.uSender
import multilib.lib.list.dto.MessageDto
import multilib.lib.list.dto.Types

import java.util.*

var stack = false
class ExecuteScript: Command {
    override val hidden: Boolean
        get() = true
    override val type: Types
        get() = Types.NO_SYNC

    private val setMapForCommand = SetMapForCommand()
    private val argsInfo = ArgsInfo()
    override suspend  fun comply(variables: HashMap<String, Any>): Result {
        stack = true
        try {
            for (i in 0 until variables.size){
                if (variables[i.toString()].toString().contains("[")){
                    operator.runCommand(variables[i.toString()].toString().drop(1))
                }else if (variables[i.toString()].toString().contains("]")){
                    if (variables[i.toString()].toString()== "]"){
                        continue
                    }else{
                        operator.runCommand(variables[i.toString()].toString().dropLast(1))
                    }
                }else{
                    operator.runCommand(variables[i.toString()].toString())
                }
            }
        }catch (e : Exception){
            uSender.print(MessageDto(emptyList(), "Ошибка. Проверьте корректность данных в скрипте."), emptyList())
        }

        stack = false
        val message = "Команда выполнена."
        return Result(message, true)
    }

    override fun getName(): String {
        return "execute_script"
    }

    override fun getDescription(): String {
        return "Исполнение команд из указанного файла. Можно передать только ОДИН аргумент."
    }

    override fun argContract(arguments: List<String>): HashMap<String, Any> {
        val file : HashMap<String, Any> = HashMap()
        for (i in 0 until arguments.size){
            file[i.toString()] = arguments[i]
        }
        return file
    }

    override fun argsInfo(): HashMap<String, Int> {
        return argsInfo.setLimits(100000, 1, 1)
    }

    override fun setMapForClient(): HashMap<String, String> {
        return setMapForCommand.setMapForCommand(1,1,true, ExecuteScript(), Var.wayToFile)
    }
}