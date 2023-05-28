package multilib.app.senders

import multilib.lib.list.dto.CommitDto
import multilib.lib.list.dto.MessageDto

interface Sender {
    fun print(message : MessageDto, list : List<CommitDto>) {
    }
}