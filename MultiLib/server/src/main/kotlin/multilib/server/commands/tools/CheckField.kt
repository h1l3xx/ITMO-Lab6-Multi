package multilib.server.commands.tools




import multilib.server.city.City
import multilib.server.commands.Var
import multilib.app.sc
import multilib.server.uSender


object Action{
    const val remove = "remove"
    const val stay = "stay"
}

class CheckField {
    private fun setField(field : String) : String{
        return when (field) {
            Var.name -> Var.name
            Var.id -> Var.id
            Var.meters -> Var.meters
            Var.age -> Var.age
            Var.agl -> Var.agl
            Var.population -> Var.population
            Var.area -> Var.area
            Var.coordinateX -> Var.coordinateX
            Var.coordinateY -> Var.coordinateY
            else -> {
                uSender.print ("Введено некорректное поле. Возможно, данное поле не поддерживает подобное удаление. Введите повторно ТОЛЬКО название поля." )
                return "error"
            }
        }
    }
    fun checkField(field : String) : String {
        return if (setField(field) == "error") {
            val newField = sc.nextLine()
            this.setField(newField)
        } else {
            return setField(field)
        }
    }
    fun removeLower(city : City, field : String, arg : String) : String{
        when (field) {
            Var.id -> {
                return if (arg.toInt() > city.getId()!!){
                    Action.remove
                }else{
                    Action.stay
                }
            }
            Var.meters -> {
                return if (arg.toLong() > city.getMetersAboveSeaLevel()!!){
                    Action.remove
                }else{
                    Action.stay
                }
            }
            Var.age -> {
                return if (arg.toInt() > city.getGovernor().getAge()!!){
                    Action.remove
                }else{
                    Action.stay
                }
            }
            Var.agl -> {
                return if (arg.toDouble() > city.getAgglomeration()!!){
                    Action.remove
                }else{
                    Action.stay
                }
            }
            Var.population ->{
                return if (arg.toLong() > city.getPopulation()!!){
                    Action.remove
                }else{
                    Action.stay
                }
            }
            Var.area -> {
                return if (arg.toInt() > city.getArea()!!){
                    Action.remove
                }else{
                    Action.stay
                }
            }
            Var.name ->{
                return if (arg > city.getName()!!){
                    Action.remove
                }else{
                    Action.stay
                }
            }
            Var.coordinateX -> {
                return if (arg.toInt() > city.getCoordinates().getX()){
                    Action.remove
                }else{
                    Action.stay
                }
            }
            else -> {
                return if (arg.toFloat() > city.getCoordinates().getY()){
                    Action.remove
                }else{
                    Action.stay
                }
            }
        }
    }
}