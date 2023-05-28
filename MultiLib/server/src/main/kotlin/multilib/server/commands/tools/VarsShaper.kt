package multilib.server.commands.tools







import multilib.server.city.Climate
import multilib.server.city.Government
import multilib.server.commands.Var
import multilib.app.sc
import multilib.server.uSender
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter


object Messages{
    const val setName = "Укажите название города (${Var.name}):"
    const val errorName = "Название города не может быть пустой строкой или null."

    const val setCoordX = "Введите координату X (${Var.coordinateX}):"
    const val setCoordY = "Введите координату Y (${Var.coordinateY}):"
    const val errorCoords = "Указано некорректное значение координаты."

    const val setArea = "Введите значение поля (${Var.area}):"
    const val errorValue = "Указано некорректное значение переменной."
    const val setPopulation = "Введите количество жителей (${Var.population}):"
    const val setMeters = "Введите высоту над уровнем моря (${Var.meters}):"
    const val setAgl = "Введите значение поля (${Var.agl})"
    const val setClimate = "Укажите климат из перечисленных значений (${Var.climate}): \n\t HUMIDCONTINENTAL, \n\t MEDITERRANIAN, \n\t STEPPE."
    const val setGovernment = "Укажите тип правительства из перечисленных (${Var.government}): \n\t OLIGARCHY, \n\t JUNTA, \n\t ETHNOCRACY."
    const val setAge = "Укажите возраст губернатора (${Var.age}):"
    const val setBirthday = "Укажите день рождения губернатора в формате DD/MM/YYYY (${Var.birthday}):"

}
class VarsShaper {
    val listForAddCommand = listOf(
        Messages.setName, Messages.setCoordX, Messages.setCoordY, Messages.setArea,
        Messages.setPopulation, Messages.setMeters, Messages.setAgl, Messages.setClimate, Messages.setGovernment,
        Messages.setAge, Messages.setBirthday
    )

    fun shape(arguments : List<String>):HashMap<String, Any>{
        val variables = HashMap<String, Any>()
        variables[Var.name] = arguments[0]
        variables[Var.coordinateX] = arguments[1]
        variables[Var.coordinateY] = arguments[2]
        variables[Var.area] = arguments[3]
        variables[Var.population] = arguments[4]
        variables[Var.meters] = arguments[5]
        variables[Var.agl] = arguments[6]
        variables[Var.climate] = arguments[7]
        variables[Var.government] = arguments[8]
        variables[Var.age] = arguments[9]
        variables[Var.birthday] = arguments[10]


        return variables
    }
    fun setName():String{
        uSender.print (Messages.setName)
        val name = sc.nextLine()
        return if (checkName(name)) {
            return name
        } else {
            this.setName()
        }
    }

    fun checkName(name : String) : Boolean {
        return if (name == "" || name.equals(null) || name == "null") {
            uSender.print(Messages.errorName)
            this.setName()
            false
        } else {
            true
        }
    }
    fun checkCoordY(y : String) : Boolean{
        return try {
            y.toFloat()
            true
        } catch (e: Exception){
            uSender.print (Messages.errorCoords)
            false
        }
    }

    fun checkCoordX(x : String): Boolean{
        return try {
            x.toLong()
            true
        } catch (e: Exception) {
            uSender.print (Messages.errorCoords)
            false
        }
    }
    fun checkAreaAndAge(area : String): Boolean{
        return try {
            area.toInt()
            if (area.toInt() < 0){
                uSender.print (Messages.errorValue)
                false}
            else{
                true
            }
        }catch (e : Exception){
            uSender.print (Messages.errorValue)
            false
        }
    }

    private fun setPopulation():Long{
        uSender.print (Messages.setPopulation)
        val population = sc.nextLine()
        return if (checkPopulation(population)) {
            return population.toLong()
        } else {
            this.setPopulation()
        }
    }

    fun checkPopulation(population : String) : Boolean{
        return try {
            population.toLong()
            if (population.toLong() < 0) {
                uSender.print (Messages.errorValue)
                this.setPopulation()
            }
            true
        } catch (e: Exception) {
            uSender.print (Messages.errorValue)
            false
        }
    }

    fun checkMeters(meters: String) : Boolean{
        return try {
            meters.toLong()
            true
        } catch (e: Exception) {
            uSender.print (Messages.errorValue)
            false
        }
    }

    fun checkAdl(agl : String) : Boolean {
        return try {
            agl.toDouble()
            true
        } catch (e: Exception) {
            uSender.print(Messages.errorValue)
            false
        }
    }

    fun checkClimate(climate : String) : Boolean{
        return try {
            Climate.valueOf(climate.uppercase())
            true
        } catch (e: Exception) {
            uSender.print (Messages.errorValue)
            false
        }
    }

    fun checkGovernment(government : String) : Boolean{
        return try {
            Government.valueOf(government.uppercase())
            true
        } catch (e: Exception) {
            uSender.print (Messages.errorValue)
            false
        }
    }
    fun checkBirthday(birthday : String) : Boolean{
        return try {
            val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            val date: LocalDate = LocalDate.parse(birthday, formatter)
            date.atStartOfDay(ZoneId.systemDefault())
            true
        } catch (e: Exception) {
            uSender.print (Messages.errorValue)
            false
        }
    }
}


