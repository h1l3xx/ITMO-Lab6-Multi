package multilib.list

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
class Serialization {
    fun serialize(data : List<HashMap<String, String>>): String? {
        val mapper = ObjectMapper()
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(data)
    }
    fun serializeAnswer(data : String) : String {
        val answer = MapBuilder().buildMap(data)
        val mapper = ObjectMapper()
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(answer)
    }
    fun serializeMap(data: HashMap<String,String>): String? {
        val mapper = ObjectMapper().registerModule(JavaTimeModule())
        return mapper.writeValueAsString(data)
    }
}