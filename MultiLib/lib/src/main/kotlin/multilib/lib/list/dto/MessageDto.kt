package multilib.lib.list.dto

import kotlinx.serialization.Serializable


@Serializable
data class MessageDto(
    var commandList : List<HashMap<String, String>> = emptyList(),
    var message : String
)