package multilib.server.commands.tools

import multilib.server.jwt.Body
import multilib.server.jwt.Builder
import java.time.ZonedDateTime
import java.util.*

class AuthManager {
    fun manage(id: Int, login: String): String {
        val builder = Builder()

        val map = mapOf(
            "login" to login
        )

        val date = ZonedDateTime.now()
        val body = Body(id, map, Date(date.year, date.monthValue, date.dayOfMonth + 1))


        return builder getToken body
    }
}