package multilib.lib.list

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class MessageDto(
    val commandList : List<HashMap<String, String>> = emptyList(),
    val message : String
)