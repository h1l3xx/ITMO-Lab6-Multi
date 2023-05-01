package multilib.app.commands

import multilib.app.commands.tools.ArgsInfo
import multilib.app.commands.tools.SetMapForCommand
import multilib.app.commands.tools.Result

class PrintAscending : Command {
    private val argsInfo = ArgsInfo()
    private val sort = Sort()
    private val show = Show()
    private val setMapForCommand = SetMapForCommand()
    override fun comply(variables: HashMap<String, Any>): Result {

        sort.comply(HashMap())
        show.comply(HashMap())


        return Result("Команда выполнена успешно.", false)
    }

    override fun getName(): String {
        return "print_ascending"
    }

    override fun getDescription(): String {
        return "Вывод элементов коллекции в естественном порядке. Передаваемых аргументов НЕТ"
    }

    override fun argsInfo(): HashMap<String, Int> {
        return argsInfo.setLimits(0,0,1)
    }

    override fun argContract(arguments: List<String>): HashMap<String, Any> {
        return HashMap()
    }

    override fun setMapForClient(): HashMap<String, String> {
        return setMapForCommand.setMapForCommand(0,0,true, PrintAscending(), "")
    }
}