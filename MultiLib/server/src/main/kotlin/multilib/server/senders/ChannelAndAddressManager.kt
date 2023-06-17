package multilib.app.senders


import multilib.server.server.UpdServer
import java.net.SocketAddress
import java.nio.channels.DatagramChannel

class ChannelAndAddressManager(channel: DatagramChannel, address: SocketAddress, server : UpdServer) {
    private var channel : DatagramChannel
    private var address  : SocketAddress
    private var server : UpdServer


    init {
        this.channel = channel
        this.address = address
        this.server = server
    }
    fun getChannel(): DatagramChannel{
        return this.channel
    }
    fun getAddress(): SocketAddress{
        return this.address
    }
    fun getServer(): UpdServer{
        return this.server
    }
}