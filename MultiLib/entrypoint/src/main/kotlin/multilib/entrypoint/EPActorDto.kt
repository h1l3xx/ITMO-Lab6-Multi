package multilib.entrypoint

import kotlinx.coroutines.CompletableDeferred
import multilib.lib.list.dto.CommitDto
import java.net.SocketAddress

data class EPActorDto(
    val type : Changes,
    val data : CommitDto?,
    val addr : SocketAddress? = null,
    var response: CompletableDeferred<Any> = CompletableDeferred()
) {
}