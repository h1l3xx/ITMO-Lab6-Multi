package multilib.entrypoint

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.channels.Channel as Ch
import multilib.lib.list.*
import multilib.lib.list.dto.CommitDto
import multilib.lib.list.dto.MessageDto
import multilib.lib.list.dto.Types

import java.net.InetSocketAddress
import java.net.SocketAddress
import java.nio.ByteBuffer
import java.nio.channels.DatagramChannel

class EntryPoint : Channel(DatagramChannel.open()) {
    private val socketAddressInterpreter = SocketAddressInterpreter()
    private var waiting = false
    private val epActor = EntryPointActor()
    private var serversList = mutableListOf<ConnectionList>()
    private var clientList = mutableListOf<ConnectionList>()
    var commits = mutableListOf<CommitDto>()
    private val scope = CoroutineScope(Job())
    private var ePToken = ""
    private var failedChannel : Ch<Request> = Ch(capacity = Ch.BUFFERED)

    var ePAddr : SocketAddress
    private val balancer = Balancer()

    init {
        val address = InetSocketAddress(Config.servAdr, 3000)
        this.channel.bind(address)
        ePAddr = this.channel.localAddress
    }
    fun start() = scope.launch{
        println("Entry Point is running.")
        launch {
            while (true) {
                val buffer = ByteBuffer.allocate(65535)
                val socketAddress: SocketAddress = channel.receive(buffer)
                buffer.flip()
                val bytes = ByteArray(buffer.remaining())
                buffer.get(bytes)
                val request = deserializeRequest(String(bytes))
                operateRequest(request, socketAddress)
                println(String(bytes))
            }
        }
        launch {
            sendToServer()
        }
        launch {
            sendAnswerToClient()
        }
        launch { sendError() }
    }
    private suspend fun sendToServer(){
        while (!waiting){
            val request = receiveChannel.receive()
            println(request)
            if (request.serversAddr.isNotEmpty()){
                send(ByteBuffer.wrap(serializeRequest(request).toByteArray()), socketAddressInterpreter.interpret(request.sender))
                waiting = true
            }else{
                send(ByteBuffer.wrap(serializeRequest(request).toByteArray()), socketAddressInterpreter.interpret(request.sender))
            }
        }
    }
    private suspend fun sendError(){
        while (true){
            val request = failedChannel.receive()
            send(ByteBuffer.wrap(serializeRequest(request).toByteArray()), socketAddressInterpreter.interpret(request.sender))
        }
    }
    private suspend fun sendAnswerToClient(){
        while (true){
            val request = sendChannel.receive()
            send(ByteBuffer.wrap(serializeRequest(request).toByteArray()), socketAddressInterpreter.interpret(request.sender))
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
    private fun operateRequest(request: Request, addr : SocketAddress) = CoroutineScope(scope.coroutineContext + Job()).launch{
        //Пришёл SocketAddress отправителя и его месага
        if (checkMess(request)){
            launch { serverManager(request, addr) }
        }else{
            launch { clientManager(request, addr) }
        }
    }
    private infix fun checkMess(request: Request) : Boolean{
            return request.who == 1
    }
    private fun serverManager(request: Request, address : SocketAddress) = CoroutineScope(Job()).launch{
        launch { checkServer(address) }
        //Добавили сервер в список (если его там не было).
        launch {
            if (request.message.message == "You can continue"){
                waiting = false
                epActor.send(
                    EPActorDto(
                    Changes.COMMITS_CLEAR, null
                )
                )
                launch {
                    sendToServer()
                }
            }
        }
        val message = request.message.message
        println(message)
        if (message.contains("try to connect")){
            launch { sendRequestToServer(Request(ePToken, address, ePAddr, 1, MessageDto(emptyList(), "success")), address) }
        }else{
            launch {
                if (request.message.message != "You can continue"){
                    resendAnswerToClient(request)
                }
            }
        }
    }
    private fun clientManager(request: Request, address: SocketAddress) =
        CoroutineScope(Job()).launch{
        if (request.message.message == "ping from client"){
            launch { successPing(address) }
        }else if (serversList.isNotEmpty()){
            launch { checkClient(address) }
            if (checkCommits(request)) {
                launch { sendRequestToServer(request, address) }
            }else{
                launch {
                    val message = "Данное действие невозможно с указанным элементом, произошли изменения."
                    sendErrorRequestToClient(SocketAddressInterpreter().interpret(request.sender), message)
                }
            }
        }else{
            launch { checkClient(address) }
            launch { sendErrorRequestToClient(address, Var.errorServer) }
        }
    }
    private suspend fun sendRequestToServer(request: Request, clientAddress : SocketAddress) =
        CoroutineScope(Job()).launch{
            val comm = epActor.getCommits(EPActorDto(Changes.GET_COMMITS, null))
            if (request.type == Types.SYNC || comm.size > 20){
                request.list = comm
                println(comm)
                request.type = Types.SYNC
                epActor.send(EPActorDto(Changes.COMMITS_CLEAR, null))
                println(comm.size)

                println(request.list.size)
                println(request.list)




                request.message.commandList = listOf(
                    mapOf(
                        "last" to "last"
                    )
                ) as List<HashMap<String, String>>
                val list = mutableListOf<String>()
                for (server in serversList){
                    list.add(server.getAddr().toString())
                }
                request.serversAddr = list
            }
            val serverAddress = balancer.balance()
            request.from = clientAddress.toString()
            request.sender = serverAddress.toString()
            launch { balancer increment serverAddress }

            launch {
                receiveChannel.send(request)
            }

    }
    private suspend fun sendErrorRequestToClient(address : SocketAddress, message : String){
        val answer = Request(ePToken, address, ePAddr, 1, MessageDto(emptyList(), message))
        failedChannel.send(answer)
    }
    private suspend infix fun resendAnswerToClient(request: Request){
        this checkCommits request.list
        val serverAddress = request.getFrom()
        balancer decrement serverAddress
        sendChannel.send(request)
    }
    private infix fun successPing(addr: SocketAddress){
        val answer = Request(ePToken, ePAddr, ePAddr, 1, MessageDto(emptyList(), "success"))
        send(ByteBuffer.wrap(serializeRequest(answer).toByteArray()), addr)
    }
    private suspend infix fun checkCommits(list : List<CommitDto>){
        if (list.isNotEmpty()){
            for (commit in list){
                epActor.send(EPActorDto(Changes.COMMITS_ADD, commit))
            }
        }
    }

    private suspend infix fun checkCommits(request : Request) : Boolean{
        val comm = epActor.getCommits(EPActorDto(Changes.GET_COMMITS, null))
        return if (comm.isNotEmpty() && this checkCommand request.message.message){
            for (commit in comm){
                val el = request.message.message.split(" ")
                if (commit.id == el[1].toInt() && commit.data.isNullOrEmpty()){
                    return false
                }
            }
            true
        }else{
            true
        }
    }

    private infix fun checkCommand(message : String): Boolean {
        return message.contains("update_by_id") || message.contains("remove_by_id")
    }
}