package multilib.lib.list.dto

import kotlinx.serialization.Serializable

@Serializable
data class SyncDto(
    val type: Types?
) {
}