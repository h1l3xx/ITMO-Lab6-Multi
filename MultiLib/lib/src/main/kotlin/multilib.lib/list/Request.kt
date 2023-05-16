package multilib.lib.list

import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

import java.net.SocketAddress

@Serializable
data class Request(val sender : String, var from : String, val who : Int, val message : MessageDto) {
    constructor(sender: SocketAddress, from: SocketAddress, who: Int, message: MessageDto): this(sender.toString(), from.toString(), who, message)

    fun getSender(): SocketAddress {
        return SocketAddressInterpreter().interpret(sender)
    }

    fun getFrom(): SocketAddress {
        return SocketAddressInterpreter().interpret(from)
    }
}

fun serializeRequest(request : Request): String {
    return Json.encodeToString(request)
}
fun deserializeRequest(message: String) : Request{
    return Json.decodeFromString<Request>(message)
}