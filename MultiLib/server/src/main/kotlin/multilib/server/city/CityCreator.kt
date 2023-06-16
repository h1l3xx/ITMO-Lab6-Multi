package multilib.server.city




import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import multilib.lib.list.dto.Act
import multilib.server.commands.Var
import multilib.lib.list.dto.CommitDto
import multilib.lib.list.dto.CommitType
import multilib.server.collectionActor
import multilib.server.commands.tools.ActorDto
import java.time.LocalDateTime
import java.time.ZonedDateTime

class CityCreator {
    suspend fun create(owner : Pair<Int, String>, creationDate : LocalDateTime, id : Long, name : String, coordX : Long, coordY : Float, area: Int, population: Long, meters: Long, agl: Double, climate:String, government: String, birthday : ZonedDateTime, age : Int):
            CommitDto
    {

        val city = City()

        city.setId(id)

        city.setOwner(owner)

        city.setCreationDate(creationDate)

        city.setName(name)

        val coordinates = Coordinates()
        coordinates.setX(coordX)
        coordinates.setY(coordY)
        city.setCoordinates(coordinates)

        city.setArea(area)

        city.setPopulation(population)

        city.setMetersAboveSeaLevel(meters)

        city.setAgglomeration(agl)

        city.setClimate(Climate.valueOf(climate))

        city.setGovernment(Government.valueOf(government))

        val governor = Human(age.toString(), birthday.toString())
        city.setGovernor(governor)

        val map : HashMap<String, String> = HashMap()
        val scope = CoroutineScope(Job())
        scope.launch {
            map[Var.id] = id.toString()
            map[Var.login] = owner.second
            map[Var.name] = name
            map[Var.coordinateX] = coordX.toString()
            map[Var.coordinateY] = coordY.toString()
            map[Var.area] = area.toString()
            map[Var.population] = population.toString()
            map[Var.meters] = meters.toString()
            map[Var.agl] = agl.toString()
            map[Var.climate] = climate
            map[Var.government] = government
            map[Var.age] = age.toString()
            map[Var.date] = creationDate.toString()
            map[Var.birthday] = birthday.toString()
        }.join()
        println("ЗДЕСЬ")
        println(city)
        scope.launch{
            collectionActor.send(
                ActorDto(
                    Pair(Act.ADD, mutableListOf(city))
                )
            )
        }.join()
        return CommitDto(CommitType.ADD ,map[Var.id]!!.toInt(), map, ZonedDateTime.now().toEpochSecond())
    }
}
