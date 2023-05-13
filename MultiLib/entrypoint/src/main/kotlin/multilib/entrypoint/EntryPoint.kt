package multilib.entrypoint

import multilib.lib.list.Channel
import multilib.lib.list.Parse
import multilib.list.Deserialization

import java.net.InetSocketAddress
import java.net.PortUnreachableException
import java.net.SocketAddress
import java.nio.ByteBuffer
import java.nio.channels.DatagramChannel

class EntryPoint : Channel(DatagramChannel.open()) {
    var serversList = mutableListOf<ConnectionList>()
    var clientList = mutableListOf<ConnectionList>()

    init {
        val address = InetSocketAddress("10.152.66.37", 3000)
        this.channel.bind(address)
    }
    fun start(){
        println("Entry Point is running.")
        while (true){
            val buffer = ByteBuffer.allocate(65535)
            val socketAddress: SocketAddress = this.channel.receive(buffer)
            buffer.flip()
            val bytes = ByteArray(buffer.remaining())
            buffer.get(bytes)
            val data = String(bytes)
            val addr = ConnectionList(socketAddress)
            if (!this.clientList.contains(addr) && this.serversList.isNotEmpty()) {
                this.clientList.add(addr)
                val freeServerAddress = this.serversList.first().getAddr()
                send(ByteBuffer.wrap(data.toByteArray()), freeServerAddress)
            } else {
                this.receive(this.channel, socketAddress, data)
            }
        }
    }

    private fun receive(channel: DatagramChannel, socketAddress: SocketAddress, data : String){
        val deserializationSegment = Deserialization().deserializeAnswer(data)
        val commandAndArguments = Parse.Parse().parseToServer(deserializationSegment)
    }
}
