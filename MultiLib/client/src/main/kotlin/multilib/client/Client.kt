package multilib.client

import multilib.client.commands.Var
import multilib.client.handkers.Connect
import multilib.lib.list.*
import multilib.lib.list.Config
import multilib.lib.list.dto.MessageDto
import multilib.lib.list.dto.Types
import java.net.InetSocketAddress
import java.net.PortUnreachableException
import java.net.SocketAddress
import java.nio.ByteBuffer
import java.nio.channels.DatagramChannel
import kotlin.system.exitProcess


var connectToEP = false

class Client : Channel(DatagramChannel.open()){

    var token = ""

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
            val req = deserializeRequest(data)
            if (req.message.message == "Вы авторизованы"){
                this.token = req.token
            }
            return req
        }catch (e : PortUnreachableException){
            connectToEP = false
            connect.tryAgain()
            return Request(this.token, channel.localAddress, channel.localAddress, 0, MessageDto(emptyList(), Var.errorEP))
        }
    }
    fun stop(line : String){
        println(line)
        println("Происходит отключение...")
        exitProcess(21)
    }
    fun sendMessage(mess: String, type: String) {
        val request = Request(this.token, channel.localAddress, entryPointAddress, 0, MessageDto(emptyList(),mess), emptyList(), null)
        if (type == Types.SYNC.toString()){
            request.type = Types.SYNC
        }else if (type == Types.NO_SYNC.toString()){
            request.type = Types.NO_SYNC
        }
        send(ByteBuffer.wrap(serializeRequest(request).toByteArray()), entryPointAddress)
    }
}