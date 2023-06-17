package multilib.server.server.actors

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.launch
import multilib.lib.list.dto.Act
import multilib.server.collection
import multilib.server.commands.tools.ActorDto
import multilib.server.databaseActor

class CollectionActor {
    private val scope = CoroutineScope(Job())

    @OptIn(ObsoleteCoroutinesApi::class)
    private val actor = scope.actor<ActorDto>(capacity = Channel.BUFFERED){
        for (command in this){
            when (command.data.first){
                Act.ADD -> run {
                    collection.add(command.data.second.first())
                    command.response.complete(1)
                }
                Act.REMOVE -> run {
                    collection.remove(command.data.second.first())
                    command.response.complete(2)
                }
                Act.UPDATE -> run {
                    collection.add(command.data.second.first())
                    command.response.complete(3)
                }
                else -> run{
                    launch { databaseActor.send(command) }
                    launch { command.response.complete(4) }
                }
            }
        }
    }
    suspend fun send(data : ActorDto): Any {
        actor.send(data)
        return data.response.await()
    }
}