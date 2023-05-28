package multilib.server.commands



import multilib.server.city.CityCompareByDefault
import multilib.server.city.CityCreator
import multilib.server.collection
import multilib.server.commands.tools.ArgsInfo
import multilib.server.commands.tools.SetMapForCommand
import multilib.server.commands.tools.VarsShaper
import multilib.server.commands.tools.Result
import multilib.lib.list.dto.CommitDto
import multilib.lib.list.dto.SyncDto
import multilib.lib.list.dto.Types
import multilib.server.database.DatabaseManager
import multilib.server.jwt.Builder
import multilib.server.uSender
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter


object Var{
    const val type = "type"
    const val login = "login"
    const val password = "password"
    const val hidden = "hidden"
    const val id = "id"
    const val name = "name"
    const val coordinateX = "coordX"
    const val coordinateY = "coordY"
    const val area = "area"
    const val population = "population"
    const val meters = "metersAboveOfSeaLevel"
    const val agl = "agglomeration"
    const val climate = "climate"
    const val government = "government"
    const val birthday = "birthday"
    const val age = "age"
    const val index = "index"
    const val allFields = "all fields"
    const val numberOfFields = "number of fields"
    const val wayToFile = "Way to File"
    const val True = "True"
    const val False = "False"
    const val numbersOfId = "numbers of id"
    const val description = "description"
    const val str = "String"
    const val long = "long"
    const val integer = "int"
    const val double = "double"
    const val float = "float"

}


class Add : Command {
    private val setMapForCommand = SetMapForCommand()
    private val shaper = VarsShaper()
    private val argsInfo = ArgsInfo()
    override val sync: SyncDto
        get() = SyncDto(Types.NO_SYNC)

    override val hidden: Boolean
        get() = true
    override fun comply(variables: HashMap<String, Any>): Result {

        val databaseManager = DatabaseManager()
        databaseManager.getConnectionToDataBase()
        val list = mutableListOf<CommitDto>()
        val creator = CityCreator()
        val name = variables[Var.name].toString()
        val coordX = variables[Var.coordinateX].toString().toLong()
        val coordY = variables[Var.coordinateY].toString().toFloat()
        val area = variables[Var.area].toString().toInt()
        val population = variables[Var.population].toString().toLong()
        val meters = variables[Var.meters].toString().toLong()
        val agl = variables[Var.agl].toString().toDouble()
        val climate = variables[Var.climate].toString()
        val government = variables[Var.government].toString()

        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val date: LocalDate = LocalDate.parse(variables[Var.birthday].toString(), formatter)
        val birt: ZonedDateTime = date.atStartOfDay(ZoneId.systemDefault())

        val owner = uSender.getToken()

        val token = Builder().verify(owner)

        val pair = Pair(token.id, token.data["login"]!!)


        val birthday = ZonedDateTime.parse(birt.toString())
        val age = variables[Var.age].toString().toInt()
        val id = databaseManager.getFreeId("collection")!!.toLong()
        val commit = creator.create(pair, LocalDateTime.now(), id, name, coordX, coordY, area, population, meters, agl, climate, government, birthday, age)
        list.add(commit)
        val c = CityCompareByDefault()
        val cl = collection.getCollection()

        cl.sortWith(c)

        return Result("Город добавлен в коллекцию", true, list)
    }

    override fun getName(): String {
        return "add"
    }

    override fun getDescription(): String {
        return "Добавление нового элемента в коллекцию. Диапазон передаваемых аргументов: от 1 до 11 (включительно)." +
                "\nПравила введения аргументов: все аргументы идти в СТРОГОМ порядке, как при обычном использовании команды add."
    }

    override fun argsInfo(): HashMap<String, Int> {
        return argsInfo.setLimits(11, 11, 0)
    }

    override fun argContract(arguments: List<String>): HashMap<String, Any> {
        return shaper.shape(arguments)
    }
    override fun setMapForClient() : HashMap<String, String>{
        return setMapForCommand.setMapForCommand(0,0,false, Add(), "")
    }
}