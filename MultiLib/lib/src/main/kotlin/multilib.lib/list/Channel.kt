package multilib.lib.list


import java.net.SocketAddress
import java.nio.ByteBuffer
import java.nio.channels.DatagramChannel

abstract class Channel(ch : DatagramChannel) {
    val channel : DatagramChannel
    init {
        this.channel = ch
    }
    fun send(message: ByteBuffer, address: SocketAddress){
        channel.send(message, address)
    }
}