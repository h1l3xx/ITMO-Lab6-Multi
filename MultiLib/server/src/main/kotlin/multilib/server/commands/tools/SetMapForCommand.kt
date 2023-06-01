package multilib.server.commands.tools


import multilib.server.commands.Add
import multilib.server.commands.Command
import multilib.app.commands.tools.Validator

class SetMapForCommand {
    fun setMapForCommand(min : Int, max : Int, inline : Boolean, command : Command, description: String) : HashMap<String, String>{
        val commandInfo = Validator()
        commandInfo.setCommandName(command.getName())
        commandInfo.setInline(inline)
        commandInfo.setMax(max)
        commandInfo.setMin(min)
        commandInfo.setHidden(command.hidden)
        commandInfo.setDto(command.type)
        if (command.getName() == Add().getName()){
            commandInfo.setText(BuilderTextForAddCommand().build(VarsShaper().listForAddCommand))
        }else{
            commandInfo.setText(description)
        }
        return commandInfo.createMap()
    }
}