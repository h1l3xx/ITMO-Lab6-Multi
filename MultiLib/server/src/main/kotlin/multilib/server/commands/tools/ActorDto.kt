package multilib.server.commands.tools

import kotlinx.coroutines.CompletableDeferred
import multilib.lib.list.dto.Act
import multilib.server.city.City

data class ActorDto(
    val data : Pair<Act, MutableList<City>>,
    var response: CompletableDeferred<Any> = CompletableDeferred()
)
