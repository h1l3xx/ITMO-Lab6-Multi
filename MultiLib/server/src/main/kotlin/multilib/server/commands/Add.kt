package multilib.server.commands



import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import multilib.server.city.CityCreator
import multilib.server.commands.tools.ArgsInfo
import multilib.server.commands.tools.SetMapForCommand
import multilib.server.commands.tools.VarsShaper
import multilib.server.commands.tools.Result
import multilib.lib.list.dto.CommitDto
import multilib.lib.list.dto.Types
import multilib.server.city.City
import multilib.server.database.DatabaseManager
import multilib.server.jwt.Body
import multilib.server.jwt.Builder
import multilib.server.uSender
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import kotlin.Long


object Var{
    const val logId = "loginId"
    const val date = "date"
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
    private val builder = Builder()
    private val setMapForCommand = SetMapForCommand()
    private val shaper = VarsShaper()
    private val argsInfo = ArgsInfo()
    val databaseManager = DatabaseManager()
    val creator = CityCreator()
    var nameCity : String? = null
    var coordX : Long? = null
    var coordY : Float? = null
    var area : Int? = null
    var population : Long? = null
    var meters : Long? = null
    var agl : Double? = null
    var climate : String? = null
    var government : String? = null
    var birt : ZonedDateTime? = null
    var localDate : LocalDateTime? = null
    var id : Long? = null
    var age : Int? = null

    var owner : String? = null
    var token : Body? = null
    var pair : Pair<Int, String>? = null
    lateinit var city : City
    override val type: Types
        get() = Types.NO_SYNC

    override val hidden: Boolean
        get() = true
    override suspend fun comply(variables: HashMap<String, Any>): Result {
        val list = mutableListOf<CommitDto>()
        val scope = CoroutineScope(Job())
        scope.launch{
            databaseManager.getConnectionToDataBase()
        }.join()
        scope.launch {
            nameCity = variables[Var.name].toString()
            coordX = variables[Var.coordinateX].toString().toLong()
            coordY = variables[Var.coordinateY].toString().toFloat()
            area = variables[Var.area].toString().toInt()
            population = variables[Var.population].toString().toLong()
            meters = variables[Var.meters].toString().toLong()
            agl = variables[Var.agl].toString().toDouble()
            climate = variables[Var.climate].toString()
            government = variables[Var.government].toString()
            age = variables[Var.age].toString().toInt()
        }.join()
        scope.launch {
            owner = uSender.getToken()
            token = try {
                builder.verify(owner!!)
            }catch (e : Exception){
                val loginMap = mapOf(
                    "login" to  variables[Var.login].toString()
                )
                Body(variables[Var.logId]!!.toString().toInt(), loginMap)
            }

            pair = Pair(token!!.id, token!!.data["login"]!!)
        }.join()
            if(variables["id"] == null){
                scope.launch {
                    val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                    val date: LocalDate = LocalDate.parse(variables[Var.birthday].toString(), formatter)
                    birt = date.atStartOfDay(ZoneId.systemDefault())
                    localDate = LocalDateTime.now()
                    id = databaseManager.getFreeId("collection")!!.toLong()
                }.join()
                scope.launch {
                    databaseManager.stop()
                }
            }else{
                scope.launch {
                    id = variables["id"]!!.toString().toLong()
                    birt = ZonedDateTime.parse(variables[Var.birthday].toString())
                    localDate = LocalDateTime.parse(variables[Var.date].toString())
                }.join()
                scope.launch {
                    databaseManager.stop()
                }
            }
        val birthday = ZonedDateTime.parse(birt.toString())


        val commit = creator.create(pair!!, localDate!!, id.toString().toLong(), nameCity!!, coordX!!, coordY!!,
            area!!, population!!, meters!!, agl!!, climate!!, government!!, birthday, age!!)

        city = creator.getCity()
        list.add(commit)
        println("Я добовляю в коллекцию ${city.getId()}")

        return Result("Город добавлен в коллекцию", true, list, city)
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