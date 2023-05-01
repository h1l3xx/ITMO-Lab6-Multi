package multilib.client.commands

import multilib.client.manager

object Var{
    const val choose = "choose"
    const val add = "add"
    const val id = "id"
    const val exec = "execute_script"
    const val name = "name"
    const val exit = "Происходит отключение от сервера..."
    const val coordinateX = "coordX"
    const val coordinateY = "coordY"
    const val area = "area"
    const val population = "population"
    const val meters = "metersAboveOfSeaLevel"
    const val agl = "agglomeration"
    const val climate = "climate"
    const val government = "government"
    const val birthday = "birthday"
    const val age = "age"
    const val index = "index"
    const val allFields = "all fields"
    const val numberOfFields = "number of fields"
    const val wayToFile = "Way to File"
    const val True = "True"
    const val False = "False"
    const val numbersOfId = "numbers of id"
    const val description = "description"
    const val str = "String"
    const val long = "long"
    const val integer = "int"
    const val double = "double"
    const val float = "float"
    const val min = "min"
    const val max = "max"
    const val between = "between"
}
val commandList : HashMap<String, HashMap<String, String>> = HashMap()
var fieldMap : HashMap<String, String> = HashMap()
class CommandList {
    fun setCommandList(list : List<HashMap<String, String>>): HashMap<String, HashMap<String, String>> {
        commandList.clear()
        fieldMap.clear()

        for (i in 0..list.size-2){
            val preMap : HashMap<String, String> = HashMap()
            preMap[Var.min] = list[i][Var.min].toString()
            preMap[Var.max] = list[i][Var.max].toString()
            preMap[Var.between] = list[i][Var.between].toString()
            preMap[Var.description] = list[i][Var.description].toString()

            commandList[list[i][Var.name].toString()] = preMap
        }
        setFieldMap(list[list.lastIndex])
        return commandList
    }

    fun showCommands(){
        val str = commandList.keys.toString().drop(1).dropLast(1)
        val list = str.split(", ")
        manager.uPrinter.print { "Список доступных команд:" }
        for (word in list){
            manager.uPrinter.print { word }
        }
        manager.uPrinter.print { "Чтобы узнать поподробнее о каждой команде, воспользуйтесь командой help." }
    }
    private fun setFieldMap(map: HashMap<String, String>): HashMap<String, String> {
        fieldMap = map
        return fieldMap
    }
}