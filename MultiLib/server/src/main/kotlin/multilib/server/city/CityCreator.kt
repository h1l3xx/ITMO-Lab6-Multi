package multilib.server.city




import multilib.server.commands.Var
import multilib.lib.list.dto.CommitDto
import multilib.server.collection
import java.time.LocalDateTime
import java.time.ZonedDateTime

class CityCreator {
    fun create(owner : Pair<Int, String>, creationDate : LocalDateTime, id : Long , name : String, coordX : Long, coordY : Float, area: Int, population: Long, meters: Long, agl: Double, climate:String, government: String, birthday : ZonedDateTime, age : Int):
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

        map[Var.id] = id.toString()
        map[Var.login] = owner.second
        map[Var.name] = name
        map[Var.coordinateX] = coordX.toString()
        map[Var.coordinateY] = coordY.toString()
        map[Var.population] = population.toString()
        map[Var.meters] = meters.toString()
        map[Var.agl] = agl.toString()
        map[Var.climate] = climate
        map[Var.government] = government
        map[Var.age] = age.toString()
        map[Var.birthday] = birthday.toString()

        collection.add(city)

        return CommitDto(map[Var.id]!!.toInt(), map, ZonedDateTime.now().toEpochSecond())
    }
}
