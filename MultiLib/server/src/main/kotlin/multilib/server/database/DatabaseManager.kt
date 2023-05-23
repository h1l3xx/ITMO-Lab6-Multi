package multilib.server.database

import java.sql.Timestamp
import multilib.app.city.City
import java.sql.*

class DatabaseManager {
    private lateinit var connection : Connection
    private lateinit var preparedStatement: PreparedStatement

    private var sql = ""


    fun getLoginWithPassword():  HashMap<String,String>{

        sql = "SELECT login, password FROM users;"
        val returnMap = HashMap<String, String>()
        try {
            preparedStatement = connection.prepareStatement(sql)

            val resultSet = preparedStatement.executeQuery()!!

            while (resultSet.next()){
                returnMap[resultSet.getString(1)] = resultSet.getString(2)
            }
        }catch (e : SQLException){
            println("Нет подключения к Базе")
            e.printStackTrace()
        }
        return returnMap
    }
    fun getConnectionToDataBase() {
        try {
            Class.forName("org.postgresql.Driver")
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }
        try {
            connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5435/postgres", "postgres", "mysecretpassword")
        } catch (e: SQLException) {
            println("Нет подключения к Базе")
            e.printStackTrace()
        }
    }
    fun registerUser(login: String, password: String) {
        sql = "INSERT INTO users VALUES(?, ?, ?)"
        try {
            preparedStatement = connection.prepareStatement(sql)

            preparedStatement.setInt(1, 2)
            preparedStatement.setString(2, login)
            preparedStatement.setString(3, password)

            preparedStatement.executeUpdate()

        } catch (e: SQLException) {
            println("Database access error")
        }
        preparedStatement.close()
        sql = ""
    }
    fun addOrg(city : City){

        try {
            this setCoord  city

            this setHuman city

            sql = "INSERT INTO collection VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);"

            preparedStatement = connection.prepareStatement(sql)
            println(preparedStatement)

            preparedStatement.setInt(1, city.getId()!!.toInt())
            preparedStatement.setInt(2, city.getId()!!.toInt())
            preparedStatement.setString(3, city.getName())
            preparedStatement.setInt(4, city.getId()!!.toInt())


            preparedStatement.setTimestamp(5, Timestamp.valueOf(city.getCreationDate()))
            preparedStatement.setInt(6, city.getArea()!!)
            preparedStatement.setLong(7, city.getPopulation()!!)
            preparedStatement.setLong(8, city.getMetersAboveSeaLevel()!!)
            preparedStatement.setDouble(9, city.getAgglomeration()!!)
            preparedStatement.setString(10, city.getClimate().toString())
            preparedStatement.setString(11, city.getGovernment().toString())
            preparedStatement.setInt(12, city.getId()!!.toInt())


            preparedStatement.executeUpdate()

            println("Done")


        } catch (e: SQLException) {

          println("Some Error")
            e.printStackTrace()
        }
        preparedStatement.close()
    }

    private infix fun setCoord(city : City){
        sql = "INSERT INTO coordinates VALUES (?, ?, ?);"
        try {
            preparedStatement = connection.prepareStatement(sql)

            preparedStatement.setInt(1, city.getId()!!.toInt())
            preparedStatement.setLong(2, city.getCoordinates().getX())
            preparedStatement.setFloat(3, city.getCoordinates().getY())

            preparedStatement.executeUpdate()
        }catch (e: SQLException) {
            e.printStackTrace()
        }
        preparedStatement.close()
    }

    private infix fun setHuman(city : City){
        sql = "INSERT INTO governors VALUES (?, ?, ?);"
        try {
            preparedStatement = connection.prepareStatement(sql)

            preparedStatement.setInt(1, city.getId()!!.toInt())
            preparedStatement.setInt(2, city.getGovernor().getAge()!!.toInt())
            println(city.getGovernor().getBirthday()!!.toLocalDateTime())
            preparedStatement.setTimestamp(3, Timestamp.valueOf(city.getGovernor().getBirthday()!!.toLocalDateTime()))

            preparedStatement.executeUpdate()
        }catch (e: SQLException) {
            e.printStackTrace()
        }
        preparedStatement.close()

    }

}