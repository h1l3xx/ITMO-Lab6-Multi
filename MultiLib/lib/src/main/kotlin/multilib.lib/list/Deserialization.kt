package multilib.list


import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.databind.ObjectMapper
import multilib.lib.list.Request
import multilib.lib.list.printers.UPrinter


class Deserialization {
//    fun deserialize(data: String): List<HashMap<String, String>> {
//
//        return try{
//            val deserializedRequest = deserializeAnswer(data)
//            val answer = deserializedRequest.getRequest().split("; ")
//            val objectMapper = ObjectMapper()
//            val returnValue = objectMapper.readValue<List<HashMap<String,String>>>(answer[answer.lastIndex])
//            returnValue
//        } catch (e : Exception){
//            if (data == "")
//                return emptyList()
//            val objectMapper = ObjectMapper()
//            if (data[0] == data[1])
//                return objectMapper.readValue<List<HashMap<String,String>>>(data.drop(1).dropLast(1))
//            else
//                return objectMapper.readValue<List<HashMap<String,String>>>(data)
//        }
//
//    }
//    fun deserializeAnswer(data: String): Request {
//        val objectMapper = ObjectMapper()
//        return objectMapper.readValue<Request>(data, Request::class.java)
//    }
}