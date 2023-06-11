package multilib.server.commands

import multilib.server.commands.tools.Result
import multilib.server.collection
import multilib.server.commands.tools.ArgsInfo
import multilib.server.commands.tools.SetMapForCommand
import multilib.lib.list.dto.CommitDto
import multilib.lib.list.dto.CommitType
import multilib.lib.list.dto.Types
import multilib.server.jwt.Builder
import multilib.server.uSender
import java.time.ZonedDateTime

class Clear : Command {
    override val hidden: Boolean
        get() = true
    override val type: Types
        get() = Types.NO_SYNC

    private val argsInfo = ArgsInfo()
    private val setMapForCommand = SetMapForCommand()
    override fun comply(variables: HashMap<String, Any>): Result {
        val list = mutableListOf<CommitDto>()
        val cityCollection = collection.getCollection()
        val iterator = cityCollection.iterator()
        while (iterator.hasNext()) {
            val iterCity = iterator.next()
            val token = Builder().verify(uSender.getToken()).data["login"]!!
            if (iterCity.getOwner().second == token) {
                list.add(CommitDto(CommitType.REMOVE, iterCity.getId()!!.toInt(), null, ZonedDateTime.now().toEpochSecond()))
                iterator.remove()
            }
        }
        return Result("Коллекция очищена.", true, list)
    }

    override fun getName(): String {
        return "clear"
    }

    override fun argContract(arguments: List<String>): HashMap<String, Any> {
        return HashMap()
    }

    override fun argsInfo(): HashMap<String, Int> {
        return argsInfo.setLimits(0,0,1)
    }

    override fun getDescription(): String {
        return "Очищение коллекции. Передаваемых аргументов НЕТ."
    }
    override fun setMapForClient(): HashMap<String, String> {
        return setMapForCommand.setMapForCommand(0,0,true, Clear(), "")
    }

}