package multilib.app.commands


import multilib.app.commands.tools.ArgsInfo

import multilib.app.commands.tools.Result
import multilib.app.commands.tools.SetMapForCommand
import multilib.app.commands.tools.VarsShaper
import multilib.app.operator
import multilib.app.uSender

import java.util.*

var stack = false
class ExecuteScript: Command{

    private val setMapForCommand = SetMapForCommand()
    private val argsInfo = ArgsInfo()
    private val shaper = VarsShaper()
    override fun comply(variables: HashMap<String, Any>): Result {
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
            uSender.print("Ошибка. Проверьте корректность данных в скрипте.")
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