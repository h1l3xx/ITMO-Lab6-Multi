package multilib.entrypoint

import kotlinx.coroutines.CompletableDeferred
import multilib.lib.list.dto.CommitDto

data class EPActorDto(
    val type : Changes,
    val data : CommitDto?,
    var response: CompletableDeferred<Any> = CompletableDeferred()
) {
}