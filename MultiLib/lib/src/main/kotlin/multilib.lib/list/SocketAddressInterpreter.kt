package multilib.lib.list

import java.net.InetSocketAddress

class SocketAddressInterpreter {
    fun interpret(address: String): InetSocketAddress {
        val pair = address.split("/")[1].split(":")
        return InetSocketAddress(pair[0], pair[1].split(";")[0].toInt())
    }

    fun fixJacksonBullshit(string: String): InetSocketAddress  {
        return this.interpret("/$string")
    }
}