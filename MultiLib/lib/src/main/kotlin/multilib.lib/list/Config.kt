package multilib.lib.list
object Var{
    const val errorServer = "Нет ни одного рабочего сервера. Повторная попытка запроса через 10 секунд."
    const val id = "id"
    const val name = "name"
    const val birthday = "birthday"
    const val description = "description"
    const val str = "String"
    const val long = "long"
    const val float = "float"

}
class Config {
    companion object{
        const val servAdr: String = "172.20.48.1"
        const val port: Int = 3000

    }
}