package multilib.server.commands.users

import multilib.server.commands.Command
import multilib.server.commands.Var
import multilib.server.commands.tools.ArgsInfo
import multilib.server.commands.tools.Result
import multilib.server.commands.tools.SetMapForCommand
import multilib.lib.list.dto.Types
import multilib.server.database.DatabaseManager

class SignUp : Command {
    private val argsInfo = ArgsInfo()
    private val databaseManager = DatabaseManager()
    private val setMapForCommand = SetMapForCommand()
    override val type: Types
        get() = Types.NO_SYNC
    override fun setMapForClient(): HashMap<String, String> {
        return setMapForCommand.setMapForCommand(2,2,false, SignUp(), Var.str + "; " + Var.str)
    }

    override fun comply(variables: HashMap<String, Any>): Result {

        var returnLine = "Вы зарегистрированы."
        var error = false
        databaseManager.getConnectionToDataBase()

        val logins = databaseManager.getLoginWithPassword().keys
        for (login in logins){
            if (login == variables[Var.login]){
                returnLine = "Пользователь с таким логином уже существует."
                error = true
                break
            }
        }
        if (!error){
            databaseManager.registerUser(variables[Var.login].toString(), variables[Var.password].toString())
            returnLine = "Вы зарегистрированы"
        }
        return Result(returnLine, true)
    }
    override val hidden: Boolean
        get() = false

    override fun getName(): String {
        return "sign_up"
    }

    override fun getDescription(): String {
        return "Регистрация пользователя"
    }

    override fun argsInfo(): HashMap<String, Int> {
        return argsInfo.setLimits(2,2,0)
    }

    override fun argContract(arguments: List<String>): HashMap<String, Any> {
        val returnMap = HashMap<String, Any>()
        returnMap[Var.login] = arguments[0]
        returnMap[Var.password] = arguments[1]
        return returnMap
    }
}