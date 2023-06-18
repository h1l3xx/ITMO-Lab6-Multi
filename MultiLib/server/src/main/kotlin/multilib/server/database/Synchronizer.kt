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
    fun synchronize(request : Request, bool : Boolean) = CoroutineScope(Job()).launch{
        var b = bool
        println(b)
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
        if (commits.isNotEmpty()){
            commits.forEach {
                when (it.type) {
                    CommitType.REMOVE -> {
                        for (city in cl) {
                            if (city.getId()!! == it.id.toLong()) {
                                removes.add(Pair(city, it))
                            }
                        }
                    }

                    CommitType.UPDATE -> {
                        for (city in cl) {
                            if (city.getId()!! == it.id.toLong()) {
                                updates.add(Pair(city, it))
                            }
                        }
                    }

                    CommitType.ADD -> {
                        var f = false
                        for (id in allId) {
                            if (id == it.id.toLong()) {
                                f = true
                            }

                        }
                        if (!f) {
                            val add = Add()
                            val city = add.comply(it.data as HashMap<String, Any>).city!!
                            println("city: $city")
                            cities.add(city)
                        }
                    }
                    CommitType.REMOVE_ALL -> {
                        cl.clear()
                    }
                }
            }
            println("THIS IS CITIES")
            println(cities)
            if (cities.size != 0){
                collectionActor.send(
                    ActorDto(
                        Pair(
                            Act.SAVE, cities
                        )
                    )
                )
            }
            println(collection.getCollection().size)
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
            println(cl.size)

        }else{
            b = false
        }
        if (b){
            resendToOtherServer(request)
        }else{
            uSender.print(MessageDto(emptyList(), "You can continue"), emptyList())
        }
    }
    private suspend infix fun resendToOtherServer(request: Request) {
        val socketAddressInterpreter = SocketAddressInterpreter()
        val servers = request.serversAddr
        if (servers.size != 1) {
            for (server in servers) {
                val addr = socketAddressInterpreter.interpret(server)
                if (addr != uSender.channel!!.localAddress) {
                    println(addr)
                    uSender setClient addr
                    uSender.print(MessageDto(emptyList(), "let's synchronize!"), request.list)
                    uSender setClient request.getFrom()
                }
            }
        }else{
            val commits = request.list
            val cities = mutableListOf<City>()
            val cl = collection.getCollection()
            commits.forEach {
                for (city in cl){
                    if (it.id.toLong() == city.getId()){
                        cities.add(city)
                    }
                }
            }
            collectionActor.send(
                ActorDto(
                    Pair(
                        Act.SAVE,
                        cities
                    )
                )
            )
        }
    }
}