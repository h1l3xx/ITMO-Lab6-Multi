package multilib.lib.list

import multilib.list.Var
import java.net.InetSocketAddress
import java.net.SocketAddress

class Parse {
    class Parse {
        fun parseToServer(map: HashMap<String, String>): String {
            var returnString = ""
            val message = map[Var.name]!!.drop(1).dropLast(1).split("; ")
            returnString += message[message.lastIndex]

            return returnString
        }
    }
}