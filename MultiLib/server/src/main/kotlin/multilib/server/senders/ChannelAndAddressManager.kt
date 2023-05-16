package multilib.app.senders


import java.net.SocketAddress
import java.nio.channels.DatagramChannel

class ChannelAndAddressManager(channel: DatagramChannel, address: SocketAddress) {
    private var channel : DatagramChannel
    private var address  : SocketAddress


    init {
        this.channel = channel
        this.address = address
    }
    fun getChannel(): DatagramChannel{
        return this.channel
    }
    fun getAddress(): SocketAddress{
        return this.address
    }
}