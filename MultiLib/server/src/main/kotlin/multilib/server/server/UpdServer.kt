package multilib.app.server


import multilib.app.commands.Clear
import multilib.app.commands.Load
import multilib.app.commands.tools.Validator
import multilib.app.operator
import multilib.app.senders.ChannelAndAddressManager
import multilib.app.uSender
import multilib.lib.list.Channel
import multilib.lib.list.Parse
import multilib.list.Deserialization
import multilib.list.Serialization

import java.net.InetSocketAddress
import java.net.SocketAddress
import java.nio.ByteBuffer
import java.nio.channels.DatagramChannel

class UpdServer : Channel(DatagramChannel.open()) {

    private var running = true
    var clientsAddress = mutableListOf<SocketAddress>()

    fun run(){

        val address = InetSocketAddress("10.152.66.37", 3000)
        this.channel.bind(address)
        println("Server is running.")

        while (this.running){
            val buffer = ByteBuffer.allocate(65535)
            val socketAddress: SocketAddress = this.channel.receive(buffer)
            buffer.flip()
            val bytes = ByteArray(buffer.remaining())
            buffer.get(bytes)
            val data = String(bytes)
            if (!this.clientsAddress.contains(socketAddress)) {
                this.clientsAddress.add(socketAddress)
                firstConnection(socketAddress)
                setChannelAndSocket(this.channel, socketAddress)
            } else {
                this.receive(this.channel, socketAddress, data)
            }
        }
    }

    private fun receive(channel: DatagramChannel, socketAddress: SocketAddress, data : String){
        val deserializationSegment = Deserialization().deserializeAnswer(data)
        val commandAndArguments = Parse.Parse().parseToServer(deserializationSegment)
        operator.runCommand(commandAndArguments.drop(1).dropLast(1))
    }

    private fun firstConnection(socketAddress: SocketAddress){
        val printAddress = socketAddress.toString().split(":")[0].drop(1)
        println("New client : $printAddress")

        Clear().comply(HashMap())
        //Load().comply(HashMap())
        this.channel.send(ByteBuffer.wrap(Serialization().serialize(Validator().takeAllInfoFromCommand())!!.toByteArray()), socketAddress)
    }
    fun stop(){
        this.running = false
    }
    private fun setChannelAndSocket(channel: DatagramChannel, socketAddress : SocketAddress){
        val manager = ChannelAndAddressManager()

        manager.setChannel(channel)
        manager.setAddress(socketAddress)

        uSender.newManager(manager)
    }
}