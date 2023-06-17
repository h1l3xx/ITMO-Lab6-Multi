package multilib.app.senders

import multilib.lib.list.dto.CommitDto
import multilib.lib.list.dto.MessageDto

interface Sender {
    suspend fun print(message : MessageDto, list : List<CommitDto>) {
    }
}