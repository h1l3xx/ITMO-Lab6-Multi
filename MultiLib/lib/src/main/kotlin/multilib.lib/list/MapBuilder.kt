package multilib.list


class MapBuilder {
    fun buildMap(line : String) : HashMap<String, String> {
        val returnMap = HashMap<String, String>()
        returnMap[Var.name] = line
        return returnMap
    }
    fun buildMapFromList(list : List<String>) : HashMap<String, String>{
        val returnMap = HashMap<String, String>()
        var returnString = "execute_script, "
        for (i in 0 until list.size){
            returnString += list[i]
        }
        returnMap[Var.name] = returnString
        return returnMap
    }
}