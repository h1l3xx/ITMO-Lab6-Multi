package multilib.app.senders

import multilib.lib.list.MessageDto

interface Sender {
    fun print(message : MessageDto) {
    }
}