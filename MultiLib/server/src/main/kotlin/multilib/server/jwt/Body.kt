package multilib.server.jwt

import kotlinx.serialization.Serializable
import java.util.*

@Serializable
class Body(
    val id: Int, val data: Map<String, String> = mapOf(),
    val expDate: Date = Date()
    ){
    companion object{
        fun setBody(id: Int, data: Map<String, String>, date: Date) : Body{
            return Body(id, data, date)
        }
    }
}