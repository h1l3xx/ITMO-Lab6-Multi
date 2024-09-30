package multilib.test

import kotlinx.coroutines.runBlocking
import multilib.client.Client
import multilib.lib.list.dto.Types
import java.time.LocalDateTime


fun main() = runBlocking{
    val start : LocalDateTime = LocalDateTime.now()
    var counter = 0
    repeat(10){
        val client = createClient("log", "pas")
        repeat(10){
            client.sendMessage("add 4 4 4.0 4 4 4 4.0 STEPPE JUNTA 4 05/02/2004 ",
                Types.NO_SYNC.toString())
            counter += 1
            println( "Город номер: $counter ${client.getMessage().message.message}")
        }
    }
    val client = createClient("log", "pas")
    client.sendMessage("info", Types.SYNC.toString())
    println(client.getMessage().message.message)
    println("Starting at $start")
    println("Finishing af ${LocalDateTime.now()}")
}
fun createClient(login : String, password : String) : Client {
    val client = Client()
    client.sendMessage("auth $login $password", Types.NO_SYNC.toString())
    client.getMessage()
    return client
}