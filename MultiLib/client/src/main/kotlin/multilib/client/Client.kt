package multilib.client

import multilib.client.commands.Var
import multilib.client.handkers.Connect
import multilib.lib.list.*
import multilib.list.Config
import java.net.InetSocketAddress
import java.net.PortUnreachableException
import java.net.SocketAddress
import java.nio.ByteBuffer
import java.nio.channels.DatagramChannel
import kotlin.system.exitProcess


var connectToEP = false

class Client : Channel(DatagramChannel.open()){
    private val entryPointAddress : SocketAddress = InetSocketAddress(Config.servAdr, Config.port)
    private val connect = Connect()
    init {
        this.channel.bind(null)
        this.channel.connect(entryPointAddress)
    }
    fun getMessage() : Request{
        try {
             var data: String? = null
             while (data.isNullOrEmpty()) {

                 val buffer: ByteBuffer = ByteBuffer.allocate(65535)

                 channel.receive(buffer)

                 buffer.flip()
                 val bytes = ByteArray(buffer.remaining())
                 buffer.get(bytes)
                 data = (String(bytes))
             }
            connectToEP = true
             return deserializeRequest(data)
        }catch (e : PortUnreachableException){
            connectToEP = false
            connect.tryAgain()
            return Request(channel.localAddress, channel.localAddress, 0, MessageDto(emptyList(), Var.errorEP))
        }
    }
    fun stop(line : String){
        println(line)
        println("Происходит отключение...")
        exitProcess(21)
    }
    infix fun sendMessage(mess: String) {
        val request = Request(channel.localAddress, entryPointAddress, 0, MessageDto(emptyList(), mess))
        send(ByteBuffer.wrap(serializeRequest(request).toByteArray()), entryPointAddress)
    }
}