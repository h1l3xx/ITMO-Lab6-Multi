package multilib.entrypoint

import java.net.SocketAddress


class Balancer {

    private var workLoud : HashMap<SocketAddress, Int> = HashMap()
    var minWork : Int = 100

    fun balance() : SocketAddress{
        val serverAddress = takeMin()
        this increment serverAddress
        return serverAddress
    }
    fun addServerToLoud(addr: SocketAddress){
        workLoud[addr] = 0
    }
    private fun takeMin() : SocketAddress{
        minWork = 100
        var returnServer = entryPoint.ePAddr
        for (server in workLoud.keys){
            if (workLoud[server]!! < minWork){
                minWork = workLoud[server]!!
                returnServer = server
            }
        }
        return returnServer
    }
    infix fun increment(addr : SocketAddress){
        workLoud[addr] = workLoud[addr]!! + 1
    }
    infix fun decrement(addr: SocketAddress){
        workLoud[addr] = workLoud[addr]!! - 1
    }
}