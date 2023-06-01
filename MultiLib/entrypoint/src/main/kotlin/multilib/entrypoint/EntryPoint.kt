package multilib.entrypoint

import multilib.lib.list.*
import multilib.lib.list.dto.CommitDto
import multilib.lib.list.dto.MessageDto
import multilib.lib.list.dto.Types

import java.net.InetSocketAddress
import java.net.SocketAddress
import java.nio.ByteBuffer
import java.nio.channels.DatagramChannel

class EntryPoint : Channel(DatagramChannel.open()) {
    private var serversList = mutableListOf<ConnectionList>()
    private var clientList = mutableListOf<ConnectionList>()
    private var commits = mutableListOf<CommitDto>()
    private var ePToken = ""

    var ePAddr : SocketAddress
    private val balancer = Balancer()

    init {
        val address = InetSocketAddress(Config.servAdr, 3000)
        this.channel.bind(address)
        ePAddr = this.channel.localAddress
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
                println("New Client is connected with $addr")
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
            sendRequestToServer(Request(ePToken, ePAddr, ePAddr, 1, MessageDto(emptyList(), "success")), address)
        }else{
            this resendAnswerToClient request
        }
    }
    private fun clientManager(request: Request, address: SocketAddress){
        if (request.message.message == "ping from client"){
            this successPing address
        }else if (serversList.isNotEmpty()){
            this checkClient address
            if (this checkCommits request) {
                sendRequestToServer(request, address)
            }else{
                //val badReq = Request(ePToken, request.sender, request.from, 0, MessageDto(emptyList(), "Невозможное изменение. Посмотрите состояние коллекции."))
                sendErrorRequestToClient(SocketAddressInterpreter().interpret(request.sender))
            }
        }else{
            this checkClient address
            sendErrorRequestToClient(address)
        }
    }
    private fun sendRequestToServer(request: Request, clientAddress : SocketAddress){
        if (request.type == Types.SYNC){
            request.list = commits
            commits = mutableListOf()
            val list = mutableListOf<String>()
            for (server in serversList){
                list.add(server.getAddr().toString())
            }
            request.serversAddr = list
        }
        val serverAddress = balancer.balance()
        request.from = clientAddress.toString()


        balancer increment serverAddress
        
        send(ByteBuffer.wrap(serializeRequest(request).toByteArray()), serverAddress)

    }
    private fun sendErrorRequestToClient(address : SocketAddress){
        val answer = Request(ePToken, ePAddr, ePAddr, 1, MessageDto(emptyList(), Var.errorServer))
        send(ByteBuffer.wrap(serializeRequest(answer).toByteArray()), address)
    }
    private infix fun resendAnswerToClient(request: Request){
        this checkCommits request.list
        val clientAddress = request.getSender()
        val serverAddress = request.getFrom()
        balancer decrement serverAddress
        println(commits)
        send(ByteBuffer.wrap(serializeRequest(request).toByteArray()), clientAddress)
    }
    private infix fun successPing(addr: SocketAddress){
        val answer = Request(ePToken, ePAddr, ePAddr, 1, MessageDto(emptyList(), "success"))
        send(ByteBuffer.wrap(serializeRequest(answer).toByteArray()), addr)
    }
    private infix fun checkCommits(list : List<CommitDto>){
        if (list.isNotEmpty()){
            for (commit in list){
                commits.add(commit)
            }
        }
    }

    private infix fun checkCommits(request : Request) : Boolean{
        return if (commits.isNotEmpty() && this checkCommand request.message.message){
            for (commit in commits){
                val el = request.message.message.split(" ")
                if (commit.id == el[1].toInt() && commit.data.isNullOrEmpty()){
                    return false
                }
            }
            true
        }else{
            println("I'm here")
            true
        }
    }

    private infix fun checkCommand(message : String): Boolean {
        return message.contains("update_by_id") || message.contains("remove_by_id")
    }
}