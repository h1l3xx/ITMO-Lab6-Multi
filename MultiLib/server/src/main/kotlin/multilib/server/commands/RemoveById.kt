package multilib.app.commands


import multilib.server.collection
import multilib.server.commands.tools.ArgsInfo
import multilib.server.commands.tools.MoreArgumentsInCommand
import multilib.server.commands.tools.Result
import multilib.server.commands.tools.SetMapForCommand
import multilib.lib.list.dto.CommitDto
import multilib.lib.list.dto.CommitType
import multilib.lib.list.dto.Types
import multilib.server.commands.Command
import multilib.server.commands.Var
import multilib.server.jwt.Builder
import multilib.server.uSender
import java.time.ZonedDateTime

private var arrayOfId = emptyArray<Long>()
class RemoveById : Command {
    override val type: Types
        get() = Types.NO_SYNC

    override val hidden: Boolean
        get() = true
    private val argsInfo = ArgsInfo()
    private val setMapForCommand = SetMapForCommand()
    private val c = collection.getCollection()

    override fun comply(variables: HashMap<String, Any>): Result {
        val numbersOfId = variables[Var.numbersOfId].toString().toInt()
        var list = mutableListOf<CommitDto>()
        var message = "Города удалены."
        if (c.size == 0){
            message = "Коллекция пуста. Нечего изменять."
        }else{
            for (id in 1..numbersOfId){
                addIdInArray(variables[id.toString()].toString().toLong())
            }
            list = removeAllCity(arrayOfId)
        }
        return Result(message, true, list)
    }

    override fun setMapForClient(): HashMap<String, String> {
        return setMapForCommand.setMapForCommand(1, 100, true, RemoveById(), Var.long)
    }

    override fun getName(): String {
        return "remove_by_id"
    }

    override fun getDescription(): String {
        return "Удаляет элемент коллекции по его id. Количество аргументов не должно превышать количества элементов в коллекции."
    }

    override fun argsInfo(): HashMap<String, Int> {
        return argsInfo.setLimits(c.size,1,1)
    }

    override fun argContract(arguments: List<String>): HashMap<String, Any> {
        val more = MoreArgumentsInCommand()
        return more.moreArguments(arguments, Var.numbersOfId)
    }
    private fun addIdInArray(id : Long){
        arrayOfId = if (arrayOfId.isNotEmpty()){
            arrayOfId.clone() + id
        } else{
            arrayOf(id)
        }
    }
    private fun removeAllCity(array : Array<Long>) : MutableList<CommitDto>{
        val list = mutableListOf<CommitDto>()
        val iterator = collection.getCollection().iterator()
        while (iterator.hasNext()) {
            val iterCity = iterator.next()
            val token = Builder().verify(uSender.getToken()).data["login"]!!
            for (id in array){
                if (iterCity.getId() == id && token == iterCity.getOwner().second) {
                    list.add(CommitDto(CommitType.REMOVE, iterCity.getId()!!.toInt(), null, ZonedDateTime.now().toEpochSecond()))
                    iterator.remove()
                }
            }
        }
        return list
    }
}