package multilib.entrypoint

import multilib.lib.list.*
import multilib.list.*

import java.net.InetSocketAddress
import java.net.SocketAddress
import java.nio.ByteBuffer
import java.nio.channels.DatagramChannel
import kotlin.system.exitProcess

class EntryPoint : Channel(DatagramChannel.open()) {
    var serversList = mutableListOf<ConnectionList>()
    var clientList = mutableListOf<ConnectionList>()

    var EPAddr : SocketAddress
    val balancer = Balancer()

    init {
        val address = InetSocketAddress(Config.servAdr, 3000)
        this.channel.bind(address)
        EPAddr = this.channel.localAddress
    }
    fun start(){
        println("Entry Point is running.")
        while (true){
            val buffer = ByteBuffer.allocate(65535)
            val socketAddress: SocketAddress = this.channel.receive(buffer)
            buffer.flip()
            val bytes = ByteArray(buffer.remaining())
            buffer.get(bytes)
            val request = deserializeRequest(String(bytes))
            operateRequest(request, socketAddress)
            println(String(bytes))
        }
    }

    private infix fun checkServer(addr : SocketAddress){
        var checker = false
        if (serversList.isEmpty()){
            serversList.add(ConnectionList(addr))
            balancer.addServerToLoud(addr)
            println("New Server is connected with $addr")

        }else{
            for (address in serversList){
                if (address.getAddr() == addr){
                    checker = true
                }
            }
            if (!checker){
                serversList.add(ConnectionList(addr))
                println("New Server is connected with $addr")
                balancer.addServerToLoud(addr)
            }
        }
    }
    private infix fun checkClient(addr: SocketAddress){
        var checker = false
        if (clientList.isEmpty()){
            clientList.add(ConnectionList(addr))
            println("New Client is connected with $addr")
        }else{
            for (address in clientList){
                if (address.getAddr() == addr){
                    checker = true
                }
            }
            if (!checker){
                clientList.add(ConnectionList(addr))
            }
        }
    }
    private fun operateRequest(request: Request, addr : SocketAddress){
        //Пришёл SocketAddress отправителя и его месага
        if (this checkMess request){
            serverManager(request, addr)
        }else{
            clientManager(request, addr)
        }
    }
    private infix fun checkMess(request: Request) : Boolean{
            return request.who == 1
    }
    private fun serverManager(request: Request, address : SocketAddress){
        this checkServer address //Добавили сервер в список (если его там не было).
        val message = request.message.message
        println(message)
        if (message.contains("try to connect")){
            sendRequestToServer(Request(EPAddr, EPAddr, 1, MessageDto(emptyList(), "success")), address)
        }else{
            this resendAnswerToClient request
        }
    }
    private fun clientManager(request: Request, address: SocketAddress){
        this checkClient address //Добавили клиента в список (если его там не было)
        if (serversList.isNotEmpty()){
            sendRequestToServer(request, address)
        }else{
            sendErrorRequestToClient(Var.errorServer, address)
        }
    }
    private fun sendRequestToServer(request: Request, clientAddress : SocketAddress){
        val serverAddress = balancer.balance()
        request.from = clientAddress.toString()
        balancer increment serverAddress
        send(ByteBuffer.wrap(serializeRequest(request).toByteArray()), serverAddress)

    }
    private fun sendErrorRequestToClient(message : String, address : SocketAddress){
        val answer = Request(EPAddr, EPAddr, 1, MessageDto(emptyList(), message))
        send(ByteBuffer.wrap(serializeRequest(answer).toByteArray()), address)
    }
    private infix fun resendAnswerToClient(request: Request){
        val clientAddress = request.getSender()
        val serverAddress = request.getFrom()
        balancer decrement serverAddress
        send(ByteBuffer.wrap(serializeRequest(request).toByteArray()), clientAddress)
    }
}
