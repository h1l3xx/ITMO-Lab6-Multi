package multilib.lib.list

import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import multilib.lib.list.dto.CommitDto
import multilib.lib.list.dto.MessageDto

import multilib.lib.list.dto.Types

import java.net.SocketAddress

@Serializable
data class Request(
    var token : String,
    var sender : String,
    var from : String,
    var who : Int,
    val message : MessageDto,
    var list : List<CommitDto>,
    var type : Types?,
    var serversAddr : List<String>
) {
    constructor(token: String, sender: SocketAddress, from: SocketAddress, who: Int, message: MessageDto): this(token, sender.toString(), from.toString(), who, message)

    constructor(token: String, sender: SocketAddress, from: SocketAddress, who: Int, message: MessageDto, list: List<CommitDto>, type: Types?):
            this (token, sender.toString(), from.toString(), who, message, list, type, emptyList())
    constructor(token: String, sender: String, from: String, who: Int, message: MessageDto) :
            this (token, sender, from, who, message, emptyList(), null, emptyList())
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