package multilib.app.senders

import multilib.server.commands.stack
import multilib.lib.list.dto.CommitDto
import multilib.lib.list.dto.MessageDto
import multilib.lib.list.Request
import multilib.lib.list.serializeRequest
import java.net.SocketAddress
import java.nio.ByteBuffer
import java.nio.channels.DatagramChannel


class USender : Sender {
    var manager : ChannelAndAddressManager? = null
    var channel : DatagramChannel? = null
    var addr : SocketAddress? = null
    var sendStack = ""
    private var clientAddress : SocketAddress? = null
    private var clientToken : String? = null
    private var lastMessage : Request? = null
    override fun print(message: MessageDto, list : List<CommitDto>) {
        if (!stack){

            if (sendStack == ""){
                channel = manager!!.getChannel()
                addr = manager!!.getAddress()
                val request = Request(clientToken!!, clientAddress!!, channel!!.localAddress!!, 1, message, list, null)
                val answerServer = serializeRequest(request)

                channel!!.send(ByteBuffer.wrap(answerServer.toByteArray()), addr)
                this setLast request
            }else{
                channel = manager!!.getChannel()
                addr = manager!!.getAddress()
                val request = Request(clientToken!!, clientAddress!!, channel!!.localAddress, 1, message, list, null)
                val answerServer = serializeRequest(request)
                channel!!.send(ByteBuffer.wrap(answerServer.toByteArray()), addr)
                this setLast request
                sendStack = ""
            }
        }else{
            sendStack += message.message +"\n"
        }
    }
    infix fun newManager(manager: ChannelAndAddressManager) {
        this.manager = manager
    }
    infix fun setClient(address : SocketAddress){
        this.clientAddress = address
    }
    infix fun setClientToken(token : String){
        this.clientToken = token
    }
    fun getToken() : String{
        return this.clientToken!!
    }
    infix fun print(line : String){
        channel = manager!!.getChannel()
        addr = manager!!.getAddress()
        val request = Request(clientToken!!, clientAddress!!, channel!!.localAddress!!, 1, MessageDto(emptyList(), line))
        val answerServer = serializeRequest(request)

        channel!!.send(ByteBuffer.wrap(answerServer.toByteArray()), addr)
        this setLast request
    }

    private fun serversInfo(info : String, serverAddress: SocketAddress){
    }
    private infix fun setLast(request: Request){
        this.lastMessage = request
    }
}