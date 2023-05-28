package multilib.entrypoint

import java.net.SocketAddress

class ConnectionList(addr: SocketAddress) {
    private val address : SocketAddress
    init {
        this.address = addr
    }
    fun getAddr(): SocketAddress{
        return this.address
    }
}