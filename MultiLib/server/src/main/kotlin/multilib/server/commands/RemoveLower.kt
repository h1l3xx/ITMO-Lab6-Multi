package multilib.app.commands

import multilib.server.collection
import multilib.lib.list.dto.CommitDto
import multilib.lib.list.dto.Types
import multilib.server.commands.Command
import multilib.server.commands.Var
import multilib.server.commands.tools.*
import multilib.server.jwt.Builder
import multilib.server.uSender
import java.time.ZonedDateTime

object Str {
    const val field = "field"
    const val arg = "arg"
}

class RemoveLower : Command {
    override val type: Types
        get() = Types.NO_SYNC
    override val hidden: Boolean
        get() = true
    private val argsInfo = ArgsInfo()
    private val checkField = CheckField()
    private val checkArg = CheckArg()
    private val setMapForCommand = SetMapForCommand()
    override fun comply(variables: HashMap<String, Any>): Result {

        val field = variables[Str.field].toString()
        val arg = variables[Str.arg].toString()
        val commits = mutableListOf<CommitDto>()
        val iterator = collection.getCollection().iterator()
        while (iterator.hasNext()) {
            val iterCity = iterator.next()
            val token = Builder().verify(uSender.getToken()).data["login"]!!
            if (checkField.removeLower(iterCity, field, arg) == Action.remove && iterCity.getOwner().second == token) {
                commits.add(CommitDto(iterCity.getId()!!.toInt(), null, ZonedDateTime.now().toEpochSecond()))
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