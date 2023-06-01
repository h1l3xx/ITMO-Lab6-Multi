package multilib.server.commands


import multilib.server.commands.tools.ArgsInfo
import multilib.server.commands.tools.Result
import multilib.lib.list.dto.Types
import multilib.server.collection
import multilib.server.database.DatabaseManager


class Save : Command {
    override val type: Types
        get() = Types.SYNC
    override val hidden: Boolean
        get() = false
    private val argsInfo = ArgsInfo()
    private val databaseManager = DatabaseManager()

    override fun comply(variables: HashMap<String, Any>): Result {
        databaseManager.getConnectionToDataBase()

        databaseManager.clearTable("collection")
        databaseManager.clearTable("governors")
        databaseManager.clearTable("coordinates")

        val cl = collection.getCollection()

        for (city in cl){
            databaseManager addCity city
        }

        return Result("Коллекция сохранена в БД", true)
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