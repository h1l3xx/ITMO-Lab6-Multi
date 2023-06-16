package multilib.server.database

import multilib.lib.list.Config
import java.sql.Timestamp
import multilib.server.city.City
import java.math.BigInteger
import java.security.MessageDigest
import java.sql.*

class DatabaseManager {
    private lateinit var connection : Connection
    private lateinit var preparedStatement: PreparedStatement

    private var sql = ""


    fun getLoginWithPassword():  HashMap<Int, Pair<String, String>>{

        sql = "SELECT * FROM users;"
        val wrapMap : HashMap<Int, Pair<String, String>> = HashMap()
        try {
            preparedStatement = connection.prepareStatement(sql)

           val result = preparedStatement.executeQuery()!!
            while (result.next()){
                wrapMap[result.getInt(1)] = Pair(result.getString(2), result.getString(3))
            }
            return wrapMap


        }catch (e : SQLException){
            println("Нет подключения к Базе")
            e.printStackTrace()
        }
        return wrapMap
    }
    fun getConnectionToDataBase() {
        try {
            Class.forName("org.postgresql.Driver")
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }
        try {
            connection = DriverManager.getConnection(
                "jdbc:postgresql://${Config.servAdr}:5435/postgres", "postgres", "mysecretpassword")
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
            val hashPass = this sha256 password
            preparedStatement.setString(3, hashPass)

            preparedStatement.executeUpdate()

        } catch (e: SQLException) {
            println("Database access error")
        }
        preparedStatement.close()
        sql = ""
    }
    infix fun addCity(city : City){

        try {
            this setCoord  city

            this setHuman city

            sql = "INSERT INTO collection VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);"

            preparedStatement = connection.prepareStatement(sql)
            println(preparedStatement)

            preparedStatement.setInt(1, city.getId()!!.toInt())
            preparedStatement.setInt(2, city.getOwner().first)
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

    infix fun sha256(input:String): String {
        val sha = MessageDigest.getInstance("SHA-256")
        return BigInteger(1, sha.digest(input.toByteArray())).toString(16).padStart(32, '0')
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
    infix fun getFreeId(tableName : String) : String? {
        sql = "SELECT nextval('${tableName}_id_seq');"
        return try {
            preparedStatement = connection.prepareStatement(sql)

            var returnLine = ""

            val resultSet = preparedStatement.executeQuery()!!

            if(resultSet.next()){
                returnLine = resultSet.getString(1)
            }
            returnLine
        }catch (e : SQLException){
            e.printStackTrace()
            "-1"
        }
    }

    fun getCollection() : ResultSet?{
        sql = "SELECT * FROM collection;"
        return try {
            preparedStatement = connection.prepareStatement(sql)

            preparedStatement.executeQuery()!!
        }catch (e : SQLException){
            e.printStackTrace()
            null
        }
    }

    fun getFromTable(tableName: String) : ResultSet?{
        sql = "SELECT * FROM $tableName;"
        return try {
            preparedStatement = connection.prepareStatement(sql)

            return preparedStatement.executeQuery()!!
        }catch (e : SQLException){
            e.printStackTrace()
            null
        }
    }
    fun clearTable(tableName : String){
        sql = "DELETE FROM $tableName;"
        try{
            preparedStatement = connection.prepareStatement(sql)
            preparedStatement.executeUpdate()
        }catch (e : SQLException){
            e.printStackTrace()
        }
    }
}