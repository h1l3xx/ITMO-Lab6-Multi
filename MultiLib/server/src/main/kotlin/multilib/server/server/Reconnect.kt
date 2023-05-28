package multilib.server.server


import java.util.*

import kotlin.concurrent.schedule

class Reconnect {
    fun reconnect(){
        Timer().schedule(10000) {
            UpdServer().run()
        }
    }
}