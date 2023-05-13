package multilib.client

import multilib.client.handkers.Connect
import multilib.lib.list.Channel
import multilib.list.Config
import multilib.list.Serialization
import java.net.InetSocketAddress
import java.net.PortUnreachableException
import java.net.SocketAddress
import java.nio.ByteBuffer
import java.nio.channels.DatagramChannel


class Client : Channel(DatagramChannel.open()){
    private val entryPointAddress : SocketAddress = InetSocketAddress(Config.servAdr, Config.port)
    init {
        this.channel.bind(null)
        this.channel.connect(entryPointAddress)
    }
    fun getMessage() : String{
        try {
             var data: String? = null
             while (data.isNullOrEmpty()) {

                 val buffer: ByteBuffer = ByteBuffer.allocate(65535)

                 channel.receive(buffer)

                 buffer.flip()
                 val bytes = ByteArray(buffer.remaining())
                 buffer.get(bytes)
                 data = (String(bytes))
             }
             return data
        }catch (e : PortUnreachableException){
             Connect().tryAgain()
             return ""
        }
    }
    fun sendMessage(mess: HashMap<String,String>) {
        send(ByteBuffer.wrap(Serialization().serializeMap(mess)!!.toByteArray()), entryPointAddress)
    }
}
    //private val serverAddress: SocketAddress = InetSocketAddress(Config.servAdr,Config.port)
            //private val channel: DatagramChannel = DatagramChannel.open()
            //
            //init {
    //    channel.bind(null)
            //    channel.connect(serverAddress)
            //    channel.configureBlocking(false)
            //}
            //
            //fun getMessage(): String {
    //    try {
    //        var data: String? = null
                    //        while (data.isNullOrEmpty()) {
//
    //            val buffer: ByteBuffer = ByteBuffer.allocate(65535)
                        //
                        //            channel.receive(buffer)
                        //
                        //            buffer.flip()
                        //            val bytes = ByteArray(buffer.remaining())
                        //            buffer.get(bytes)
                        //            data = (String(bytes))
                        //        }
                    //        return data
                    //    }catch (e : PortUnreachableException){
    //        Connect().tryAgain()
                    //        return ""
                    //    }
                //}
            //
            //fun sendMessage(mess: HashMap<String,String>) {
    //    channel.send(ByteBuffer.wrap(Serialization().serializeMap(mess)!!.toByteArray()), serverAddress)
                //}