package multilib.lib.list.dto

import kotlinx.serialization.Serializable


@Serializable
data class CommitDto(
    val id: Int,
    val data: HashMap<String, String>?,
    val timestamp: Long
) {
}