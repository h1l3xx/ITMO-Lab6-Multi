package multilib.lib.list.dto

import kotlinx.serialization.Serializable


@Serializable
data class MessageDto(
    val commandList : List<HashMap<String, String>> = emptyList(),
    var message : String
)