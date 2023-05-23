package multilib.server.server

import multilib.server.commands.Save
import java.util.*
import kotlin.collections.HashMap
import kotlin.concurrent.schedule

class Reconnect {
    fun reconnect(){
        Timer().schedule(10000) {
            UpdServer().run()
        }
    }
}