package multilib.app.city




import multilib.app.collection
import java.time.LocalDateTime
import java.time.ZonedDateTime

class CityCreator {
    fun create(name : String, coordX : Long, coordY : Float, area: Int, population: Long, meters: Long, agl: Double, climate:String, government: String, birthday : ZonedDateTime, age : Int){
        val city = City()
        city.setId(collection.getFreeId())

        city.setCreationDate(LocalDateTime.now())

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


        collection.add(city)

    }
}
