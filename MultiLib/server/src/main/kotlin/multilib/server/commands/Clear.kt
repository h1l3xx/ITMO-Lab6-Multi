package multilib.app.commands

import multilib.app.commands.tools.Result
import multilib.app.collection
import multilib.app.commands.tools.ArgsInfo
import multilib.app.commands.tools.SetMapForCommand

class Clear : Command {

    private val argsInfo = ArgsInfo()
    private val setMapForCommand = SetMapForCommand()
    override fun comply(variables: HashMap<String, Any>): Result {

        val cityCollection = collection.getCollection()
        cityCollection.clear()

        return Result("Коллекция очищена.", true)
    }

    override fun getName(): String {
        return "clear"
    }

    override fun argContract(arguments: List<String>): HashMap<String, Any> {
        return HashMap()
    }

    override fun argsInfo(): HashMap<String, Int> {
        return argsInfo.setLimits(0,0,1)
    }

    override fun getDescription(): String {
        return "Очищение коллекции. Передаваемых аргументов НЕТ."
    }
    override fun setMapForClient(): HashMap<String, String> {
        return setMapForCommand.setMapForCommand(0,0,true, Clear(), "")
    }

}