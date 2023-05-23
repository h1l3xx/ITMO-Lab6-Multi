package multilib.app.senders

import multilib.app.commands.stack
import multilib.lib.list.MessageDto
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
    private var lastMessage : Request? = null
    override fun print(message: MessageDto) {
        if (!stack){

            if (sendStack == ""){
                channel = manager!!.getChannel()
                addr = manager!!.getAddress()
                val request = Request(clientAddress!!, channel!!.localAddress!!, 1, message)
                val answerServer = serializeRequest(request)

                channel!!.send(ByteBuffer.wrap(answerServer.toByteArray()), addr)
                this setLast request
            }else{
                channel = manager!!.getChannel()
                addr = manager!!.getAddress()
                val request = Request(clientAddress!!, channel!!.localAddress, 1, message)
                val answerServer = serializeRequest(request)
                channel!!.send(ByteBuffer.wrap(answerServer.toByteArray()), addr)
                this setLast request
                sendStack = ""
            }
        }else{
            sendStack += message.message +"\n"
        }
    }
    fun newManager(manager: ChannelAndAddressManager) {
        this.manager = manager
    }
    fun setClient(address : SocketAddress){
        this.clientAddress = address
    }
    fun print(line : String){
        channel = manager!!.getChannel()
        addr = manager!!.getAddress()
        val request = Request(clientAddress!!, channel!!.localAddress!!, 1, MessageDto(emptyList(), line))
        val answerServer = serializeRequest(request)

        channel!!.send(ByteBuffer.wrap(answerServer.toByteArray()), addr)
        this setLast request
    }
    private infix fun setLast(request: Request){
        this.lastMessage = request
    }
    fun resend(){
        channel = manager!!.getChannel()
        addr = manager!!.getAddress()
        val answer = serializeRequest(this.lastMessage!!)
        channel!!.send(ByteBuffer.wrap(answer.toByteArray()), addr)
    }
}