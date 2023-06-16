package multilib.server.commands

import multilib.server.commands.tools.ArgsInfo
import multilib.server.commands.tools.Result
import multilib.server.commands.tools.SetMapForCommand
import multilib.lib.list.dto.Types
import java.util.*
import kotlin.collections.HashMap
import kotlin.concurrent.schedule

class Long  : Command {
    override val type: Types
        get() = Types.NO_SYNC
    override val hidden: Boolean
        get() = false
    private val argsInfo = ArgsInfo()
    private val setMapForCommand = SetMapForCommand()
    override fun getName(): String {
        return "long"
    }

    override fun getDescription(): String {
        return "Убивает сервер на 10 секунд."
    }

    override fun argContract(arguments: List<String>): HashMap<String, Any> {
        return HashMap()
    }

    override fun argsInfo(): HashMap<String, Int> {
        return argsInfo.setLimits(0,0,0)
    }

    override suspend fun comply(variables: HashMap<String, Any>): Result {
        Timer().schedule(10000) {
            println("bob")
        }
        return Result("Команда выполнена", true)
    }

    override fun setMapForClient(): HashMap<String, String> {
        return setMapForCommand.setMapForCommand(0,0,true, Long(), "")
    }
}