package multilib.client.commands



import multilib.client.manager

class CheckScript {
    private val scriptArray = arrayOf("")
    fun check(line : String) : Boolean{
        for (script in scriptArray){
            if (line == script){
                manager.uPrinter.print { "Ошибка. Обнаружена рекурсия." }
                return false

            }else{
                scriptArray[scriptArray.size-1] = line
            }
        }
        return true
    }
}