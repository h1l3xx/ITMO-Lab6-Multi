package multilib.entrypoint

import java.net.SocketAddress

class ConnectionList(addr: SocketAddress) {
    private val address : SocketAddress
    private var from : Int = -1
    init {
        this.address = addr
    }
    fun setFrom(value : Int){
        this.from = value
    }
    fun getAddr(): SocketAddress{
        return this.address
    }
}