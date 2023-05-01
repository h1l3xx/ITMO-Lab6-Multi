package multilib.list


import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.databind.ObjectMapper
import multilib.lib.list.printers.UPrinter


class Deserialization {
    fun deserialize(data: String): List<HashMap<String, String>> {
        return try{
            val objectMapper = ObjectMapper()
            val returnValue = objectMapper.readValue<List<HashMap<String,String>>>(data)
            returnValue
        } catch (e : Exception){
            UPrinter().print { "Отсутствует подключение к серверу. Повторная попытка через 10 секунд." }
            return emptyList()
        }

    }
    fun deserializeAnswer(data: String): HashMap<String, String> {
        val objectMapper = ObjectMapper()
        return objectMapper.readValue<HashMap<String,String>>(data)
    }
}