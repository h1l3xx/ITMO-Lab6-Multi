package multilib.client.commands

class CheckFiled {
    fun check(f: String) : Boolean{
        var checker = false
        val listOfFields = fieldMap.keys
        for (field in listOfFields){
            if (f == field && field != Var.id){
                checker = true
            }
        }
        return checker
    }
    fun getType(filed: String) : String{
        return fieldMap[filed].toString()
    }
}