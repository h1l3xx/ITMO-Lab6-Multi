package multilib.server.commands

import multilib.server.commands.tools.Result
import multilib.lib.list.dto.SyncDto

interface Command {

    val sync : SyncDto

    val hidden : Boolean
    fun comply(variables: HashMap<String, Any>): Result
    fun getDescription(): String
    fun getName(): String
    fun argContract(arguments : List<String>): HashMap<String, Any>
    fun argsInfo(): HashMap<String, Int>
    fun setMapForClient(): HashMap<String, String>
}