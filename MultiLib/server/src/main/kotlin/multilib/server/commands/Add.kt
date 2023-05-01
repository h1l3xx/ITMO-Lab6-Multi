package multilib.app.commands



import multilib.app.city.CityCompareByDefault
import multilib.app.city.CityCreator
import multilib.app.collection
import multilib.app.commands.tools.ArgsInfo
import multilib.app.commands.tools.SetMapForCommand
import multilib.app.commands.tools.VarsShaper
import multilib.app.commands.tools.Result
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter


object Var{
    const val save = "C:\\Users\\Sasha\\IdeaProjects\\Lab6-Server\\save.json"
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
    override fun comply(variables: HashMap<String, Any>): Result {


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

        val birthday = ZonedDateTime.parse(birt.toString())
        val age = variables[Var.age].toString().toInt()
        creator.create(name, coordX, coordY, area, population, meters, agl, climate, government, birthday, age)

        val c = CityCompareByDefault()
        val cl = collection.getCollection()

        cl.sortWith(c)

        return Result("Город добавлен в коллекцию", true)
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