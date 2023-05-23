package multilib.server.commands.users

import multilib.app.commands.Command
import multilib.app.commands.Var
import multilib.app.commands.tools.ArgsInfo
import multilib.app.commands.tools.CheckArg
import multilib.app.commands.tools.Result
import multilib.app.commands.tools.SetMapForCommand
import multilib.server.database.DatabaseManager

class Authorization : Command{

    private val argsInfo = ArgsInfo()
    private val setMapForCommand = SetMapForCommand()
    private val databaseManager = DatabaseManager()

    override fun comply(variables: HashMap<String, Any>): Result {
        var returnLine = "Неправильно введен login или password."

        val login = variables[Var.login].toString()
        val password = variables[Var.password].toString()
        println("login: $login")
        println("password: $password")

        databaseManager.getConnectionToDataBase()
        for (pair in databaseManager.getLoginWithPassword()) {
            if (pair.key == login && pair.value == password) {
                returnLine = "Вы авторизованы."
                break
            }
        }
        return Result(returnLine, true)
    }

    override fun setMapForClient(): HashMap<String, String> {
        return setMapForCommand.setMapForCommand(2,2,false,Authorization(), Var.str + "; " + Var.str)
    }
    override val hidden: Boolean
        get() = false

    override fun argsInfo(): HashMap<String, Int> {
        return argsInfo.setLimits(2,2,0)
    }

    override fun getName(): String {
        return "auth"
    }

    override fun getDescription(): String {
        return "Авторизация пользователя."
    }

    override fun argContract(arguments: List<String>): HashMap<String, Any> {
        val returnMap = HashMap<String, Any>()

        returnMap[Var.login] = arguments[0]
        returnMap[Var.password] = arguments[1]
        return returnMap
    }
}