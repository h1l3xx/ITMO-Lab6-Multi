package multilib.entrypoint

import java.net.SocketAddress


class Balancer {

    private var workLoud : MutableMap<SocketAddress, Pair<Int, Boolean>> = HashMap()

    var minWork : Int = 100000000

    fun balance() : SocketAddress{
        val serverAddress = takeMin()
        this increment serverAddress
        println(serverAddress)
        println(workLoud)
        println("-----------------------------------")
        return serverAddress
    }
    fun addServerToLoud(addr: SocketAddress){
        workLoud[addr] = Pair(0, false)
        println("workloud")
        println(workLoud)
    }
    private fun takeMin() : SocketAddress{
        println("1")
        println(workLoud)
        var bool: SocketAddress? = null
        workLoud = workLoud.map{
            if (!it.value.second && bool == null){
                println(it.key)
                bool = it.key
                Pair(it.key, Pair(it.value.first, true))
            }else{
                Pair(it.key, Pair(it.value.first, it.value.second))
            }
        }.toMap().toMutableMap()
        if (bool != null){
            return bool as SocketAddress
        }

//        workLoud.forEach{
//            if (!it.value.second){
//                it.value.second = true
//                println(it.key)
//                return@takeMin it.key
//            }
//        }

        println(workLoud)
        workLoud = workLoud.map {
            Pair(it.key, Pair(it.value.first, false))
        }.toMap().toMutableMap()
        println(workLoud)
        return takeMin()
    }
    infix fun increment(addr : SocketAddress){
        workLoud[addr] = Pair(workLoud[addr]!!.first + 1, workLoud[addr]!!.second)
    }
    infix fun decrement(addr: SocketAddress){
        workLoud[addr] = Pair(workLoud[addr]!!.first - 1, workLoud[addr]!!.second)
    }
}