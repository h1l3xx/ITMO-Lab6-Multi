package multilib.server.database

import multilib.app.senders.USender
import multilib.lib.list.Request
import multilib.lib.list.SocketAddressInterpreter
import multilib.server.collection
import multilib.server.commands.Save
import multilib.server.commands.tools.CityUpdater
import multilib.server.uSender

class Synchronizer {
    fun synchronize(request : Request){
        val cl = collection.getCollection()
        val cityUpdater = CityUpdater()
        val commits  = request.list
        commits.forEach {
            if (it.data.isNullOrEmpty()){
                for (city in cl){
                    if (city.getId()!! == it.id.toLong()) {
                        cl.remove(city)
                    }
                }

            }else{
                for (city in cl){
                    if (city.getId()!! == it.id.toLong()){
                        cityUpdater.updateCity(city, it.data!!)
                    }
                }
            }
            Save().comply(HashMap())
            resendToOtherServer(request)
        }
    }
    private infix fun resendToOtherServer(request: Request){
        val sender = USender()
        val socketAddressInterpreter = SocketAddressInterpreter()
        val servers = request.serversAddr
        for (server in servers){
            val addr = socketAddressInterpreter.interpret(server)
            if (addr != uSender.channel!!.localAddress){
                sender.channel = uSender.channel
                sender.setClient(addr)
                sender.print("let's synchronize!")
            }
        }
    }
}