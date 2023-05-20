package multilib.app.server


import multilib.app.commands.Clear
import multilib.app.commands.tools.Validator
import multilib.app.operator
import multilib.app.senders.ChannelAndAddressManager
import multilib.app.uSender
import multilib.lib.list.*
import multilib.list.Config

import java.net.InetSocketAddress
import java.net.SocketAddress
import java.nio.ByteBuffer
import java.nio.channels.DatagramChannel

class UpdServer : Channel(DatagramChannel.open()) {

    private var running = true
    var clientsAddress = mutableListOf<SocketAddress>()
    private val entryPointAddress : SocketAddress = InetSocketAddress(Config.servAdr, Config.port)

    fun run(){

        this.channel.bind(null)
        this.channel.connect(entryPointAddress)
        val request = Request(this.channel.localAddress, entryPointAddress, 1, MessageDto(emptyList(), "try to connect"))
        this.channel.send(ByteBuffer.wrap(serializeRequest(request).toByteArray()), entryPointAddress)
        println("Server is running.")

        while (this.running){
            val buffer = ByteBuffer.allocate(65535)
            val socketAddress: SocketAddress = this.channel.receive(buffer)
            buffer.flip()
            val bytes = ByteArray(buffer.remaining())
            buffer.get(bytes)
            val data = String(bytes)
            if (data.contains("success")) {
                this.clientsAddress.add(socketAddress)
                firstConnection(socketAddress, this.channel)
            } else {
                this.receive(data)
            }
        }
    }

    private fun receive(data : String){
        val deserializationRequest = deserializeRequest(data)
        val address = deserializationRequest.getFrom()

        uSender.setClient(address)

        val commandAndArguments = deserializationRequest.message

        if (checkForCommandList(commandAndArguments.message)){
            val map = Validator().takeAllInfoFromCommand()

            uSender.print(MessageDto(map, "It's all commands"))
        }else{

            println(deserializationRequest.message.message)
        operator.runCommand(deserializationRequest.message.message)
        }
    }

    private fun firstConnection(socketAddress: SocketAddress, channel: DatagramChannel){
        println("Successful connection to Entry Point")
        setChannelAndSocket(socketAddress, channel)
        Clear().comply(HashMap())
    }
    fun stop(){
        this.running = false
    }
    private fun setChannelAndSocket(socketAddress: SocketAddress, channel: DatagramChannel){
        val manager = ChannelAndAddressManager(channel, socketAddress)

        uSender.newManager(manager)
    }
    private fun checkForCommandList(command : String) : Boolean{
        return command.contains("commandList")
    }
}