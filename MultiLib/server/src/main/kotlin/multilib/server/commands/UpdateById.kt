package multilib.app.commands


import multilib.app.collection
import multilib.app.commands.tools.*


class UpdateById : Command {
    private val argsInfo = ArgsInfo()
    private val updater = CityUpdater()
    private var detector = false
    private val setMapForCommand = SetMapForCommand()
    override fun comply(variables: HashMap<String, Any>): Result {
        val c = collection.getCollection()
        var message = "Значение полей города обновлены."
        if (c.size == 0){
            message = "Коллекция пуста. Нечего изменять."
        }else{
            val iterator = collection.getCollection().iterator()
            while (!detector && iterator.hasNext()) {
                val iterCity = iterator.next()
                if (iterCity.getId() == variables[Var.id].toString().toLong()) {
                    updater.updateCity(iterCity, variables)
                    detector = true
                }
            }
            if (!this.detector){
                message = "Города с таким id не существует."
            }
        }
        return Result(message, true)
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