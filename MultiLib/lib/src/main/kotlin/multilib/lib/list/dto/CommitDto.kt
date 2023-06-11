package multilib.lib.list.dto

import kotlinx.serialization.Serializable


@Serializable
data class CommitDto(
        var type : CommitType,
        val id: Int,
        val data: HashMap<String, String>?,
        val timestamp: Long
) {
}