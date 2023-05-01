package multilib.app.senders


import java.net.SocketAddress
import java.nio.channels.DatagramChannel

class ChannelAndAddressManager {
    private var channel : DatagramChannel? = null
    private var address  : SocketAddress? = null

    fun setChannel(ch : DatagramChannel){
        this.channel = ch
    }
    fun getChannel(): DatagramChannel{
        return this.channel!!
    }
    fun setAddress(addr : SocketAddress){
        this.address = addr
    }
    fun getAddress(): SocketAddress{
        return this.address!!
    }
}