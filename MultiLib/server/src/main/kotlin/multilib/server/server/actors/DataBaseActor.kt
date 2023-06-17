package multilib.server.server.actors

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.actor
import multilib.lib.list.dto.Act
import multilib.server.city.City
import multilib.server.commands.Load
import multilib.server.commands.Save
import multilib.server.commands.tools.ActorDto

class DataBaseActor {
    private val scope = CoroutineScope(Job())
    @OptIn(ObsoleteCoroutinesApi::class)
    private val actor = scope.actor<ActorDto>(capacity = Channel.BUFFERED) {
        for (command in this) {
            when (command.data.first) {
                Act.LOAD -> run{
                    load()
                    command.response.complete(5)
                }
                Act.SAVE -> run{
                    save(command.data.second)
                    command.response.complete(6)
                }
                else -> {
                    println("Данный тип не поддерживается")
                }
            }
        }
    }

    private suspend fun load(){
        Load().comply(HashMap())
    }
    private suspend fun save(list : MutableList<City>){
        Save().comply(list)
    }
    suspend fun send(data : ActorDto): Any {
        actor.send(data)
        return data.response.await()
    }
}