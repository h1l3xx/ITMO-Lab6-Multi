package multilib.app.commands


import multilib.app.commands.tools.ArgsInfo
import multilib.app.commands.tools.ParseToSave
import multilib.app.commands.tools.Result
import multilib.app.commands.tools.SetMapForCommand


class Save : Command {
    private val argsInfo = ArgsInfo()
    private val parseToSave = ParseToSave()
    private val setMapForCommand = SetMapForCommand()
    override fun comply(variables: HashMap<String, Any>): Result {
        parseToSave.save()
        return Result("Коллекция сохранена в файл", true)
    }

    override fun setMapForClient(): HashMap<String, String> {
        return HashMap()
    }

    override fun getDescription(): String {
        return "Сохранение коллекции в файл. Передаваемых аргументов НЕТ."
    }

    override fun getName(): String {
        return "save"
    }

    override fun argsInfo(): HashMap<String, Int> {
        return argsInfo.setLimits(0,0,1)
    }

    override fun argContract(arguments: List<String>): HashMap<String, Any> {
        return HashMap()
    }
}