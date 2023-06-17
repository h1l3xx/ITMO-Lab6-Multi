package multilib.server.database

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import multilib.lib.list.Request
import multilib.lib.list.SocketAddressInterpreter
import multilib.lib.list.dto.Act
import multilib.lib.list.dto.CommitDto
import multilib.lib.list.dto.CommitType
import multilib.lib.list.dto.MessageDto
import multilib.server.city.City
import multilib.server.city.CityCreator
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
        val cities = mutableListOf<City>()
        val updates = mutableListOf<Pair<City, CommitDto>>()
        val removes = mutableListOf<Pair<City, CommitDto>>()
        val commits  = request.list
        commits.forEach {
            when (it.type){
                CommitType.REMOVE -> {
                    for (city in cl){
                        if (city.getId()!! == it.id.toLong()){
                            removes.add(Pair(city, it))
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
                        val add = Add()
                        add.comply(it.data as HashMap<String, Any>)
                        cities.add(add.city)
                    }
                }CommitType.REMOVE_ALL ->{
                    collection.getCollection().clear()
                }
            }
        }
        launch {
            updates.forEach{
                cityUpdater.updateCity(it.first, it.second.data!!)
            }
        }
        launch {
            removes.forEach{
                collectionActor.send(
                    ActorDto(
                        Pair(Act.REMOVE, mutableListOf(it.first))
                    )
                )
            }
        }
        collectionActor.send(
            ActorDto(
                Pair(Act.SAVE, cities)
            )
        )
        launch { resendToOtherServer(request) }
    }
    private suspend infix fun resendToOtherServer(request: Request) {
        val socketAddressInterpreter = SocketAddressInterpreter()
        println("Size is:")
        println(request.list.size)
        val servers = request.serversAddr
        for (server in servers) {
            val addr = socketAddressInterpreter.interpret(server)
            if (addr != uSender.channel!!.localAddress) {
                println(addr)
                uSender setClient addr
                uSender.print(MessageDto(emptyList(), "let's synchronize!"), emptyList())
                uSender setClient request.getFrom()
            }
        }
    }
}