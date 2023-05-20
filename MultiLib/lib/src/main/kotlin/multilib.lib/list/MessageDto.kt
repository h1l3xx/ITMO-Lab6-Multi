package multilib.lib.list

import kotlinx.serialization.Serializable


@Serializable
data class MessageDto(
    val commandList : List<HashMap<String, String>> = emptyList(),
    val message : String
)