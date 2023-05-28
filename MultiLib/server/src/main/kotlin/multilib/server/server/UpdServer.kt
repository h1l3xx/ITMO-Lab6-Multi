package multilib.server.server


import multilib.server.commands.Load
import multilib.app.commands.tools.Validator
import multilib.server.operator
import multilib.app.senders.ChannelAndAddressManager
import multilib.server.uSender
import multilib.lib.list.*
import multilib.lib.list.printers.UPrinter
import multilib.lib.list.Config
import multilib.lib.list.dto.MessageDto

import java.net.InetSocketAddress
import java.net.PortUnreachableException
import java.net.SocketAddress
import java.nio.ByteBuffer
import java.nio.channels.DatagramChannel

class UpdServer : Channel(DatagramChannel.open()) {

    private var running = true
    private val uPrinter = UPrinter()
    private var serverToken = ""

    private val entryPointAddress : SocketAddress = InetSocketAddress(Config.servAdr, Config.port)

    fun run(){

        this.channel.bind(null)
        this.channel.connect(entryPointAddress)
        val request = Request(serverToken, this.channel.localAddress, entryPointAddress, 1, MessageDto(emptyList(), "try to connect"))
        this.channel.send(ByteBuffer.wrap(serializeRequest(request).toByteArray()), entryPointAddress)
        uPrinter.print { "Server is trying to connect to Entry Point." }

        while (this.running){
            try {
                val buffer = ByteBuffer.allocate(65535)
                val socketAddress: SocketAddress = this.channel.receive(buffer)
                buffer.flip()
                val bytes = ByteArray(buffer.remaining())
                buffer.get(bytes)
                val data = String(bytes)
                if (data.contains("success")) {

                    firstConnection(socketAddress, this.channel)

                } else {
                    this.receive(data)
                }
            } catch (e : PortUnreachableException){
                uPrinter.print { "Отсутствует подключение к EP, повторная попытка через 10 секунд." }
                Reconnect().reconnect()
            }
        }
    }

    private fun receive(data : String){
        val req = deserializeRequest(data)
        val address = req.getFrom()

        uSender setClient address
        uSender setClientToken req.token

        val message = req.message.message

        if (req.token == "" && message != "commandList" && !message.contains("auth") && !message.contains("sign_up")){
            uSender.print("Вы не авторизованы")
        }else{
            val commandAndArguments = req.message

            if (checkForCommandList(commandAndArguments.message)){
                val map = Validator().takeAllInfoFromCommand()

                uSender.print(MessageDto(map, "It's all commands"), emptyList())
            }else{

                println(req.message.message)
                operator.runCommand(req.message.message)
            }
        }
    }

    private fun firstConnection(socketAddress: SocketAddress, channel: DatagramChannel){
        println("Successful connection to Entry Point")
        setChannelAndSocket(socketAddress, channel)

        Load().comply(HashMap())

    }
    private fun setChannelAndSocket(socketAddress: SocketAddress, channel: DatagramChannel){
        val manager = ChannelAndAddressManager(channel, socketAddress)

        uSender newManager manager
    }
    private fun checkForCommandList(command : String) : Boolean{
        return command.contains("commandList")
    }
}