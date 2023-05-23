package multilib.server.commands.users

import multilib.app.commands.Command
import multilib.app.commands.Var
import multilib.app.commands.tools.ArgsInfo
import multilib.app.commands.tools.Result
import multilib.app.commands.tools.SetMapForCommand
import multilib.server.database.DatabaseManager

class SingUp : Command {
    private val argsInfo = ArgsInfo()
    private val databaseManager = DatabaseManager()
    private val setMapForCommand = SetMapForCommand()

    override fun setMapForClient(): HashMap<String, String> {
        return setMapForCommand.setMapForCommand(2,2,false, SingUp(), Var.str + "; " + Var.str)
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
        return "sing_up"
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