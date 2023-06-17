package multilib.entrypoint

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.actor
import multilib.lib.list.dto.CommitDto
import java.util.LinkedList

class EntryPointActor {
    private val scope = CoroutineScope(Job())
    @OptIn(ObsoleteCoroutinesApi::class)
    private val actor = scope.actor<EPActorDto>(capacity = Channel.BUFFERED){
        for (change in this){
            when (change.type){
                Changes.COMMITS_CLEAR -> run {
                    entryPoint.commits = mutableListOf()
                    change.response.complete(1)
                }
                Changes.COMMITS_ADD -> {
                    entryPoint.commits.add(change.data!!)
                    change.response.complete(2)
                }
                Changes.GET_COMMITS -> {
                    change.response.complete(entryPoint.commits)
                }
            }
        }
    }
    suspend fun send(data : EPActorDto): Any {
        actor.send(data)
        return data.response.await()
    }
    suspend fun getCommits(data : EPActorDto): MutableList<CommitDto>{
        actor.send(data)
        return data.response.await() as MutableList<CommitDto>
    }

}