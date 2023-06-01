package multilib.server.commands.users

import multilib.server.commands.Command
import multilib.server.commands.Var
import multilib.server.commands.tools.ArgsInfo
import multilib.server.commands.tools.Result
import multilib.server.commands.tools.SetMapForCommand
import multilib.lib.list.dto.Types
import multilib.server.commands.tools.AuthManager
import multilib.server.database.DatabaseManager
import multilib.server.uSender

class Authorization : Command {
    override val type: Types
        get() = Types.NO_SYNC

    private val argsInfo = ArgsInfo()
    private val setMapForCommand = SetMapForCommand()
    private val databaseManager = DatabaseManager()
    private val authManager = AuthManager()

    override fun comply(variables: HashMap<String, Any>): Result {
        var returnLine = "Неправильно введен login или password."

        val login = variables[Var.login].toString()
        val password = variables[Var.password].toString()
        println("login: $login")
        println("password: $password")

        databaseManager.getConnectionToDataBase()
        val resultReq = databaseManager.getLoginWithPassword()
        val allId = resultReq.keys.toMutableList()
        val logins = resultReq.values.toMutableList()

        for (i in 0 until resultReq.size){
            if (logins[i].first == login && logins[i].second == databaseManager sha256 password){
                val token = authManager.manage(allId[i], login)
                uSender setClientToken token
                returnLine = "Вы авторизованы"
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