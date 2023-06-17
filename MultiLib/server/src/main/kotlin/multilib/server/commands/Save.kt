package multilib.server.commands


import multilib.lib.list.dto.MessageDto
import multilib.server.commands.tools.ArgsInfo
import multilib.server.commands.tools.Result
import multilib.lib.list.dto.Types
import multilib.server.city.City
import multilib.server.collection
import multilib.server.database.DatabaseManager
import multilib.server.uSender


class Save : Command {
    override val type: Types
        get() = Types.SYNC
    override val hidden: Boolean
        get() = false
    private val argsInfo = ArgsInfo()
    private val databaseManager = DatabaseManager()

    override suspend fun comply(variables: HashMap<String, Any>): Result {
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

    suspend fun comply(list : MutableList<City>){
        databaseManager.getConnectionToDataBase()


        list.forEach {
            println(it)
            databaseManager.addCity(it)
        }
        uSender.print(MessageDto(emptyList(), "You can continue"), emptyList())
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