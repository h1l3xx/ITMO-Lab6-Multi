package multilib.app.commands
import multilib.app.commands.tools.ArgsInfo
import multilib.app.commands.tools.Parser
import multilib.app.commands.tools.Result
import multilib.server.database.DatabaseManager

class Load : Command {
    val argsInfo = ArgsInfo()
    override val hidden: Boolean
        get() = true
    private val databaseManger = DatabaseManager()

    override fun comply(variables: HashMap<String, Any>): Result {



        return Result("Загружено", true)
    }

    override fun getName(): String {
        return "load"
    }

    override fun getDescription(): String {
        return "Загружает информацию из файла в коллекцию."
    }

    override fun argsInfo(): HashMap<String, Int> {
        return argsInfo.setLimits(1,1,0)
    }

    override fun argContract(arguments: List<String>): HashMap<String, Any> {
        val map = HashMap<String, Any>()
        map[Var.wayToFile] = arguments[0]
        return map
    }

    override fun setMapForClient(): HashMap<String, String> {
        return HashMap()
    }
}