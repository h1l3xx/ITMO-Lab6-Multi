package multilib.server.database

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import multilib.lib.list.Request
import multilib.lib.list.SocketAddressInterpreter
import multilib.lib.list.dto.Act
import multilib.lib.list.dto.CommitDto
import multilib.lib.list.dto.CommitType
import multilib.server.city.City
import multilib.server.collection
import multilib.server.collectionActor
import multilib.server.commands.Add
import multilib.server.commands.tools.ActorDto
import multilib.server.commands.tools.CityUpdater
import multilib.server.uSender
import java.util.*
import kotlin.collections.HashMap

@Suppress("UNCHECKED_CAST")
class Synchronizer {
    fun synchronize(request : Request) = CoroutineScope(Job()).launch{
        val cl = collection.getCollection()
        val allId = LinkedList<Long>()
        for (city in cl){
            allId.add(city.getId()!!)
        }
        val cityUpdater = CityUpdater()
        val updates = mutableListOf<Pair<City, CommitDto>>()
        val commits  = request.list
        commits.forEach {
            when (it.type){
                CommitType.REMOVE -> {
                    for (city in cl){
                        if (city.getId()!! == it.id.toLong()){
                            cl.remove(city)
                        }
                    }
                }
                CommitType.UPDATE ->{
                    for (city in cl){
                        if (city.getId()!! == it.id.toLong()){
                            updates.add(Pair(city, it))
                        }
                    }
                }CommitType.ADD ->{
                    var f = false
                    for (id in allId){
                        if (id == it.id.toLong()){
                            f = true
                        }
                    }
                    if (!f){
                        Add().comply(it.data as HashMap<String, Any>)
                    }
                }
            }
        }
        launch {
            updates.forEach{
                cityUpdater.updateCity(it.first, it.second.data!!)
            }
        }
        launch {
            collectionActor.actor.send(
                ActorDto(
                    Pair(Act.SAVE, collection.getCollection())
                )
            )
        }

        resendToOtherServer(request)
    }
    private infix fun resendToOtherServer(request: Request) {
        val socketAddressInterpreter = SocketAddressInterpreter()
        val servers = request.serversAddr
        for (server in servers) {
            val addr = socketAddressInterpreter.interpret(server)
            if (addr != uSender.channel!!.localAddress) {
                println(addr)
                uSender setClient addr
                uSender.print("let's synchronize!")
                uSender setClient request.getFrom()
            }
        }
    }
}