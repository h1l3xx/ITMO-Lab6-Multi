package multilib.server.commands


import multilib.server.collection
import multilib.server.commands.tools.ArgsInfo
import multilib.server.commands.tools.CheckArg
import multilib.server.commands.tools.Result
import multilib.server.commands.tools.SetMapForCommand
import multilib.lib.list.dto.CommitDto
import multilib.lib.list.dto.Types
import java.time.ZonedDateTime


class RemoveAt : Command {
    override val type: Types
        get() = Types.NO_SYNC
    override val hidden: Boolean
        get() = true
    private val argsInfo = ArgsInfo()
    private val checkArg = CheckArg()
    private val setMapForCommand = SetMapForCommand()
    override fun comply(variables: HashMap<String, Any>): Result {
        val message : String
        val list = mutableListOf<CommitDto>()
        val argument = variables[Var.index].toString().toInt()

        val cl = collection.getCollection()

        message = if (cl.size-1 < argument){
            "В коллекции нет города под таким индексом."
        }else{
            list.add(CommitDto(cl[argument].getId()!!.toInt(), null, ZonedDateTime.now().toEpochSecond()))
            cl.removeAt(argument)
            "Город с указанным индексом удален."
        }


        return Result(message, true, list)
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
        return setMapForCommand.setMapForCommand(1,1,true, RemoveAt(), Var.integer)
    }
}