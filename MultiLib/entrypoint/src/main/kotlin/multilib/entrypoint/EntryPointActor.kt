package multilib.entrypoint

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.actor
import multilib.lib.list.dto.CommitDto
import java.net.SocketAddress
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
                Changes.GET_SERVERS -> {
                    change.response.complete(entryPoint.serversList)
                }
                Changes.BALANCE_ADD -> {
                    entryPoint.balancer.addServerToLoud(change.addr!!)
                    change.response.complete(2)
                }
                Changes.BALANCE -> {
                    change.response.complete(entryPoint.balancer.balance())
                }
                Changes.BALANCE_INC -> {
                    entryPoint.balancer.increment(change.addr!!)
                    change.response.complete(1)
                }
                Changes.BALANCE_D -> {
                    entryPoint.balancer.decrement(change.addr!!)
                    change.response.complete(1)
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
    suspend fun getServers(data : EPActorDto): MutableList<ConnectionList>{
        actor.send(data)
        return  data.response.await() as MutableList<ConnectionList>
    }
    suspend fun getServer(data : EPActorDto) : SocketAddress{
        actor.send(data)
        return data.response.await() as SocketAddress
    }
}