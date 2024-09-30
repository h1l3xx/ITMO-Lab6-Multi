package multilib.server.server


import kotlinx.coroutines.*

import multilib.app.commands.tools.Validator
import multilib.server.operator
import multilib.app.senders.ChannelAndAddressManager
import multilib.server.uSender
import multilib.lib.list.*
import multilib.lib.list.printers.UPrinter
import multilib.lib.list.Config
import multilib.lib.list.dto.Act
import multilib.lib.list.dto.MessageDto
import multilib.server.collectionActor
import multilib.server.commands.tools.ActorDto

import java.net.InetSocketAddress
import java.net.PortUnreachableException
import java.net.SocketAddress
import java.nio.ByteBuffer
import java.nio.channels.DatagramChannel

class UpdServer : Channel(DatagramChannel.open()) {

    private var running = true
    private val uPrinter = UPrinter()
    private var serverToken = ""
    private val scope = CoroutineScope(Job())

    private val entryPointAddress : SocketAddress = InetSocketAddress(Config.servAdr, Config.port)


    private suspend fun connect(sc: CoroutineScope) = CoroutineScope(Job() + sc.coroutineContext).launch {
        launch {
            try {
                val buffer = ByteBuffer.allocate(65535)
                val socketAddress: SocketAddress = channel.receive(buffer)
                buffer.flip()
                val bytes = ByteArray(buffer.remaining())
                buffer.get(bytes)
                val data = String(bytes)
                if (data.contains("success")) {
                    println("Successful connection to Entry Point")
                    setChannelAndSocket(socketAddress, channel)
                }
                println(data)
            }catch (e : PortUnreachableException){
                    uPrinter.print { "Отсутствует подключение к EP, повторная попытка через 10 секунд." }
                    Reconnect().reconnect()
            }
        }.join()
        receive().join()
    }
    private fun receive() = CoroutineScope(Job() + scope.coroutineContext).launch {
        while (running){
            try {
                val buffer = ByteBuffer.allocate(65535)
                channel.receive(buffer)
                buffer.flip()
                val bytes = ByteArray(buffer.remaining())
                buffer.get(bytes)
                val data = String(bytes)
                println(data)
                receiveChannel.trySend(deserializeRequest(data))

            } catch (e : PortUnreachableException){
                uPrinter.print { "Отсутствует подключение к EP, повторная попытка через 10 секунд." }
                Reconnect().reconnect()
            }
        }
    }

    fun run() = scope.launch {

        receiveData(this)
        launch{
                channel.bind(null)
                channel.connect(entryPointAddress)
                val request = Request(
                    serverToken,
                    channel.localAddress,
                    entryPointAddress,
                    1,
                    MessageDto(emptyList(), "try to connect")
                )
                channel.send(ByteBuffer.wrap(serializeRequest(request).toByteArray()), entryPointAddress)
                uPrinter.print { "Server is trying to connect to Entry Point." }
        }.join()
        launch {
            collectionActor.send(
                ActorDto(
                    Pair(Act.LOAD, mutableListOf())
                )
            )
        }
        launch {
            receiveToSend()
        }
        connect(this).join()
    }
    private suspend fun receiveToSend(){
        while (true){
            try {
                val request = sendChannel.receive()
                uSender send request
            }catch (e : Exception){
                println("Some exception")
                e.printStackTrace()
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun receiveData(sc : CoroutineScope) = CoroutineScope(Job() + sc.coroutineContext).launch{
        while (true){
            if (!receiveChannel.isEmpty){
                val req = receiveChannel.tryReceive().getOrThrow()
                println(req)
                val address = req.getFrom()
                uSender setClient address
                uSender setClientToken req.token
                val message = req.message.message
                if (req.token == "" && message != "commandList" && !message.contains("auth") && !message.contains("sign_up")
                    && message != "let's synchronize!"){
                    uSender.print(MessageDto(emptyList(), "Вы не авторизованы"), emptyList())
                }else{
                    val commandAndArguments = req.message
                    if (checkForCommandList(commandAndArguments.message)){
                        val map = Validator().takeAllInfoFromCommand()
                        uSender.print(MessageDto(map, "It's all commands"), emptyList())
                    }else{
                        println(req.message.message)
                        launch {
                            operator.checkSync(req)
                        }
                    }
                }
            }
        }
    }
    private fun setChannelAndSocket(socketAddress: SocketAddress, channel: DatagramChannel){
        val manager = ChannelAndAddressManager(channel, socketAddress, this)

        uSender newManager manager
    }
    private fun checkForCommandList(command : String) : Boolean{
        return command.contains("commandList")
    }
}