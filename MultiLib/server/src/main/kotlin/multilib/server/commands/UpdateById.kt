package multilib.app.commands


import multilib.server.collection
import multilib.lib.list.dto.SyncDto
import multilib.lib.list.dto.Types
import multilib.server.commands.Command
import multilib.server.commands.Var
import multilib.server.commands.tools.*
import multilib.server.jwt.Builder
import multilib.server.uSender


class UpdateById : Command {
    override val sync: SyncDto
        get() = SyncDto(Types.NO_SYNC)
    override val hidden: Boolean
        get() = true
    private val argsInfo = ArgsInfo()
    private val updater = CityUpdater()
    private var detector = false
    private val setMapForCommand = SetMapForCommand()
    override fun comply(variables: HashMap<String, Any>): Result {
        val c = collection.getCollection()
        var result = Result("", true)
        if (c.size == 0){
            result.message = "Коллекция пуста. Нечего изменять."
        }else{
            val iterator = collection.getCollection().iterator()
            val token = Builder().verify(uSender.getToken()).data["login"]!!
            while (!detector && iterator.hasNext()) {
                val iterCity = iterator.next()
                if (iterCity.getId() == variables[Var.id].toString().toLong() && token == iterCity.getOwner().second) {
                    result = updater.updateCity(iterCity, variables)
                    result.message = "Значение полей города обновлены."
                    detector = true
                }
            }
            if (!this.detector){
                result.message = "Города с таким id не существует."
            }
        }
        return result
    }

    override fun getName(): String {
        return "update_by_id"
    }

    override fun getDescription(): String {
        return "Удаляет элемент из коллекции по его id. Диапазон принимаемых аргументов: от 1 до 12.\n" +
                "Правила введения аргументов: Первый - id, далее название полей, которые нуждаются в изменении."

    }

    override fun argsInfo(): HashMap<String, Int> {
        return argsInfo.setLimits(12,1,1)
    }

    override fun argContract(arguments: List<String>): HashMap<String, Any> {
        val firstMap : HashMap<String, Any> = HashMap()

        if (arguments.size == 1){
            firstMap[Var.allFields] = Var.True
        }else{
            firstMap[Var.allFields] = Var.False
        }

        firstMap[Var.id] = arguments[0].toInt()

        val fields : List<String> = arguments.drop(1)

        val more = MoreArgumentsInCommand()
        val secondMap = more.moreArguments(fields, Var.numberOfFields)
        return (firstMap + secondMap) as HashMap<String, Any>
    }

    override fun setMapForClient(): HashMap<String, String> {
        return setMapForCommand.setMapForCommand(1, 12, true, UpdateById(), Var.allFields)
    }
}