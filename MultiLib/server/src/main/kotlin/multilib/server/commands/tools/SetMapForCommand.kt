package multilib.app.commands.tools


import multilib.app.commands.Add
import multilib.app.commands.Command

class SetMapForCommand {
    fun setMapForCommand(min : Int, max : Int, inline : Boolean, command : Command, description: String) : HashMap<String, String>{
        val commandInfo = Validator()
        commandInfo.setCommandName(command.getName())
        commandInfo.setInline(inline)
        commandInfo.setMax(max)
        commandInfo.setMin(min)
        commandInfo.setHidden(command.hidden)
        if (command.getName() == Add().getName()){
            commandInfo.setText(BuilderTextForAddCommand().build(VarsShaper().listForAddCommand))
        }else{
            commandInfo.setText(description)
        }
        return commandInfo.createMap()
    }
}