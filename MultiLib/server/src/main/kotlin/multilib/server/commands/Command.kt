package multilib.server.commands

import multilib.lib.list.dto.Types
import multilib.server.commands.tools.Result

interface Command {

    val type : Types

    val hidden : Boolean
    suspend fun comply(variables: HashMap<String, Any>): Result
    fun getDescription(): String
    fun getName(): String
    fun argContract(arguments : List<String>): HashMap<String, Any>
    fun argsInfo(): HashMap<String, Int>
    fun setMapForClient(): HashMap<String, String>
}