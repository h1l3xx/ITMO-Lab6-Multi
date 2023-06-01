package multilib.app.commands.tools

import multilib.app.commandList
import multilib.lib.list.dto.Types
import multilib.server.commands.Var
import multilib.server.commands.tools.SetMapForArguments
import multilib.server.commands.tools.Values


class Validator {
    private var list = mutableListOf<HashMap<String,String>>()
    private var max : Int? = null
    private var min : Int? = null
    private var inline : Boolean? = null
    private var commandName : String? = null
    private var text : String? = null
    private var hidden : Boolean? = null
    private var type : Types? = null

    fun setHidden(hidden : Boolean){
        this.hidden = hidden
    }

    fun setCommandName(name : String){
        this.commandName = name
    }
    fun setMax(value : Int){
        this.max = value
    }
    fun setMin(value : Int){
        this.min = value
    }
    fun setText(value : String){
        this.text = value
    }
    fun setInline(value: Boolean){
        this.inline = value
    }
    fun setDto(dto : Types){
        this.type = dto
    }
    fun createMap() : HashMap<String, String>{
        val returnMap : HashMap<String, String> = HashMap()
        returnMap[Var.name] = commandName.toString()
        returnMap[Values.max] = max.toString()
        returnMap[Values.min] = min.toString()
        returnMap[Values.between] = inline.toString()
        returnMap[Var.description] = text.toString()
        returnMap[Var.hidden] = hidden.toString()
        returnMap[Var.type] = type.toString()

        return returnMap
    }


    fun takeAllInfoFromCommand() : List<HashMap<String,String>>{
        for (command in commandList){
            list.add(command.value.setMapForClient())
        }
        list.add(SetMapForArguments().set() as HashMap<String, String>)
        return list
    }
}