package multilib.app.commands

import multilib.app.city.arrayFreeId
import multilib.server.collection
import multilib.app.commands.tools.*

object Str {
    const val field = "field"
    const val arg = "arg"
}

class RemoveLower : Command {

    override val hidden: Boolean
        get() = true
    private val argsInfo = ArgsInfo()
    private val checkField = CheckField()
    private val checkArg = CheckArg()
    private val setMapForCommand = SetMapForCommand()
    override fun comply(variables: HashMap<String, Any>): Result {

        val field = variables[Str.field].toString()
        val arg = variables[Str.arg].toString()

        val iterator = collection.getCollection().iterator()
        while (iterator.hasNext()) {
            val iterCity = iterator.next()
            if (checkField.removeLower(iterCity, field, arg) == Action.remove) {
                arrayFreeId = if (arrayFreeId.isNotEmpty()){
                    arrayFreeId.clone() + iterCity.getId()!!
                } else{
                    arrayOf(iterCity.getId()!!)
                }
                iterator.remove()
            }
        }

        return Result("Города, у которых значение указанного поля меньше - удалены.", true)
    }

    override fun getName(): String {
        return "remove_lower"
    }

    override fun setMapForClient(): HashMap<String, String> {
        return setMapForCommand.setMapForCommand(2,2,true,RemoveLower(), Var.str + "; field" )
    }

    override fun getDescription(): String {
        return "Удаляет из коллекции все элементы, меньшие, чем заданный. Передается ДВА аргумента: (поле) и (значение)."
    }

    override fun argsInfo(): HashMap<String, Int> {
        return argsInfo.setLimits(2,2,0)
    }

    override fun argContract(arguments: List<String>): HashMap<String, Any> {
        val arg : HashMap<String, Any> = HashMap()
        arg[Str.field] = checkField.checkField(arguments[0])
        arg[Str.arg] = checkArg.checkArg(arguments[0] ,arguments[1])

        return arg
    }
}