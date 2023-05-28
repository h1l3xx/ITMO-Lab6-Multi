package multilib.server.commands.tools

import multilib.lib.list.dto.CommitDto

class Result(var message: String, val bool: Boolean, private val list: List<CommitDto>) {
    constructor(message: String, bool: Boolean) :
            this(message, bool, emptyList())
    fun getCommit() : List<CommitDto>{
        return this.list
    }
}