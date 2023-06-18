package multilib.server.commands
import multilib.server.city.CityCreator
import multilib.server.commands.tools.ArgsInfo
import multilib.server.commands.tools.Result
import multilib.lib.list.dto.Types
import multilib.server.collection
import multilib.server.database.DatabaseManager
import java.sql.ResultSet
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import kotlin.Long

class Load : Command {
    val argsInfo = ArgsInfo()
    override val hidden: Boolean
        get() = true

    private val databaseManger = DatabaseManager()
    override val type: Types
        get() = Types.NO_SYNC

    override suspend fun comply(variables: HashMap<String, Any>): Result {



        val cityCreator = CityCreator()


        databaseManger.getConnectionToDataBase()

        val preCollection = databaseManger.getCollection()!!
        val coordinates = databaseManger.getFromTable("coordinates")!!
        val governors = databaseManger.getFromTable("governors")!!

        while (preCollection.next()){

            val id = preCollection.getInt(1).toLong()
            val loginId = preCollection.getInt(2)

            val owner : Pair<Int, String> = getLogin(loginId)!!

            val name = preCollection.getString(3)!!
            val coordinateId = preCollection.getInt(4)

            val coords = getCoordinates(coordinateId, coordinates)!!

            val coordX = coords.first
            val coordY = coords.second

            val creationDate = preCollection.getDate(5).toLocalDate()!!
            val area = preCollection.getInt(6)
            val pop = preCollection.getLong(7)
            val meters = preCollection.getLong(8)
            val agl = preCollection.getDouble(9)
            val climate = preCollection.getString(10)!!
            val government = preCollection.getString(11)!!
            val governorId = preCollection.getInt(12)

            val governor = getGovernor(governorId, governors)!!

            val governorAge = governor.first
            val governorBirt = governor.second

            cityCreator.create(owner, creationDate.atStartOfDay(), id, name, coordX, coordY, area, pop, meters, agl, climate, government, governorBirt, governorAge)

        }
        databaseManger.stop()
        println("загружено?")
        println( collection.getCollection().size)

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

    private fun getCoordinates(id : Int, coordinates : ResultSet) : Pair<Long, Float>? {
        while(coordinates.next()){
            if (coordinates.getInt(1) == id){
                return Pair(coordinates.getLong(2), coordinates.getFloat(3))
            }
        }
        return null
    }

    private fun getGovernor(id : Int, governors : ResultSet): Pair<Int, ZonedDateTime>? {
        while (governors.next()){
            if (governors.getInt(1) == id){
                val birthday = governors.getString(3)!!.slice(0..9)
                val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                val date: LocalDate = LocalDate.parse(birthday, formatter)
                val result: ZonedDateTime = date.atStartOfDay(ZoneId.systemDefault())
                return Pair(governors.getInt(2), result)
            }
        }
        return null
    }

    private fun getLogin(loginId : Int): Pair<Int, String>? {
        val users = databaseManger.getFromTable("users")!!

        while(users.next()){
            if (users.getInt(1) == loginId){
                 return Pair(loginId, users.getString(2))
            }
        }
        return null
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