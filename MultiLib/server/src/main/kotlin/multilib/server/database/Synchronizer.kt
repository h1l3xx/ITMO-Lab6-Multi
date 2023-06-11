package multilib.server.database

import multilib.app.city.City
import multilib.app.city.CityCreator
import multilib.app.collection
import multilib.app.uSender
import multilib.lib.list.Request
import multilib.lib.list.SocketAddressInterpreter
import multilib.lib.list.dto.CommitDto
import multilib.lib.list.dto.CommitType
import multilib.server.commands.tools.CityUpdater
import java.util.*

class Synchronizer {
    fun synchronize(request : Request){
        val cl = collection.getCollection()
        val cityUpdater = CityUpdater()
        val cityCreator = CityCreator()
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
                    println(it.data)
                }
            }
        }
        updates.forEach{
            cityUpdater.updateCity(it.first, it.second.data!!)
        }
        resendToOtherServer(request)
    }
    private infix fun resendToOtherServer(request: Request){
        val socketAddressInterpreter = SocketAddressInterpreter()
        val servers = request.serversAddr
        for (server in servers){
            val addr = socketAddressInterpreter.interpret(server)
            if (addr != uSender.channel!!.localAddress){
                println(addr)
                uSender setClient addr
                uSender.print("let's synchronize!")
                uSender setClient request.getFrom()
            }
        }
    }
}