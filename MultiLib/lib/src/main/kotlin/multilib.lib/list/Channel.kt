package multilib.lib.list




import kotlinx.coroutines.channels.Channel
import java.net.SocketAddress
import java.nio.ByteBuffer
import java.nio.channels.DatagramChannel

abstract class Channel(ch : DatagramChannel) {
    val channel : DatagramChannel
    init {
        this.channel = ch
    }
    var receiveChannel : Channel<Request> = Channel(capacity = Channel.BUFFERED)
    var sendChannel : Channel<Request> = Channel(capacity = Channel.BUFFERED)
    fun send(message: ByteBuffer, address: SocketAddress){
        channel.send(message, address)
    }
}
