package multilib.server.commands

import multilib.lib.list.dto.Act
import multilib.server.collection
import multilib.server.commands.tools.ArgsInfo
import multilib.server.commands.tools.Result
import multilib.server.commands.tools.SetMapForCommand
import multilib.lib.list.dto.CommitDto
import multilib.lib.list.dto.CommitType
import multilib.lib.list.dto.Types
import multilib.server.city.City
import multilib.server.collectionActor
import multilib.server.commands.tools.ActorDto
import multilib.server.jwt.Builder
import multilib.server.uSender
import java.time.ZonedDateTime


class RemoveAllByMetersAboveSeaLevel: Command {

    override val hidden: Boolean
        get() = true
    val commits = mutableListOf<CommitDto>()
    private val argsInfo = ArgsInfo()
    private val setMapForCommand = SetMapForCommand()
    override val type: Types
        get() = Types.NO_SYNC
    override suspend fun comply(variables: HashMap<String, Any>): Result {
        val arr = mutableListOf<City>()

        val iterator = collection.getCollection().iterator()
        val token = Builder().verify(uSender.getToken()).data["login"]!!
        while (iterator.hasNext()) {
            val iterCity = iterator.next()
            if (iterCity.getMetersAboveSeaLevel() == variables[Var.meters] && token == iterCity.getOwner().second) {
                commits.add(CommitDto(CommitType.REMOVE, iterCity.getId()!!.toInt(), null, ZonedDateTime.now().toEpochSecond()))
                arr.add(iterCity)
            }
            arr.forEach {
                collectionActor.send(
                    ActorDto(
                        Pair(Act.REMOVE, mutableListOf(it))
                    )
                )
            }
        }

        return Result("Удалены все города, с указанным значением.", true, commits)
    }

    override fun getName(): String {
        return "remove_all_by_meters_above_sea_level"
    }

    override fun getDescription(): String {
        return "Удаляет из коллекции все элементы, значение поля metersAboveSeaLevel которого эквивалентно заданному. Один передаваемых аргумент."
    }

    override fun argsInfo(): HashMap<String, Int> {
        return argsInfo.setLimits(1,1,1)
    }

    override fun argContract(arguments: List<String>): HashMap<String, Any> {
        val arg : HashMap<String, Any> = HashMap()
        arg[Var.meters] = arguments[0].toLong()
        return arg
    }

    override fun setMapForClient(): HashMap<String, String> {
        return setMapForCommand.setMapForCommand(1,1,true, RemoveAllByMetersAboveSeaLevel(), Var.long)
    }
}