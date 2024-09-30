package multilib.server.commands.tools

import multilib.lib.list.dto.CommitDto
import multilib.server.city.City

class Result(var message: String, val bool: Boolean, private val list: List<CommitDto>, val city : City?) {
    constructor(message: String, bool: Boolean) :
            this(message, bool, emptyList(), null)
    constructor(message: String, bool: Boolean, list: List<CommitDto>) :
            this(message, bool, list, null)
    fun getCommit() : List<CommitDto>{
        return this.list
    }
}