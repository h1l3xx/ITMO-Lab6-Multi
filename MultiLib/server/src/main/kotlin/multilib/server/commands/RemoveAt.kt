package multilib.app.commands


import multilib.app.city.arrayFreeId
import multilib.app.collection
import multilib.app.commands.tools.ArgsInfo
import multilib.app.commands.tools.CheckArg
import multilib.app.commands.tools.Result
import multilib.app.commands.tools.SetMapForCommand


class RemoveAt : Command {
    private val argsInfo = ArgsInfo()
    private val checkArg = CheckArg()
    private val setMapForCommand = SetMapForCommand()
    override fun comply(variables: HashMap<String, Any>): Result {
        val message : String

        val argument = variables[Var.index].toString().toInt()

        val cl = collection.getCollection()

        message = if (cl.size-1 < argument){
            "В коллекции нет города под таким индексом."
        }else{
            arrayFreeId = if (arrayFreeId.isNotEmpty()){
                arrayFreeId.clone() + cl[0].getId()!!
            } else{
                arrayOf(cl[0].getId()!!)
            }
            cl.removeAt(argument)
            "Город с указанным индексом удален."
        }


        return Result(message, true)
    }

    override fun getName(): String {
        return "remove_at"
    }

    override fun getDescription(): String {
        return "Удаляет элемент, находящийся в заданной позиции коллекции (index). Один аргумент."
    }

    override fun argsInfo(): HashMap<String, Int> {
        return argsInfo.setLimits(1,1,1)
    }

    override fun argContract(arguments: List<String>): HashMap<String, Any> {
        val arg = HashMap<String, Any>()
        arg[Var.index] = checkArg.checkArg(Var.id, arguments[0])
        return arg
    }

    override fun setMapForClient(): HashMap<String, String> {
        return setMapForCommand.setMapForCommand(1,1,true,RemoveAt(), Var.integer)
    }
}