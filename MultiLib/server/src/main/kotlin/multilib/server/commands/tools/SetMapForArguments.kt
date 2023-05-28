package multilib.server.commands.tools



import multilib.server.commands.Var

class SetMapForArguments {
    fun set() : Map<String, String>{
        return mapOf(
            Var.name to Var.str,
            Var.coordinateX to Var.long,
            Var.coordinateY to Var.float,
            Var.area to Var.integer,
            Var.population to Var.long,
            Var.meters to Var.long,
            Var.agl to Var.double,
            Var.climate to "choose: HUMIDCONTINENTAL MEDITERRANIAN STEPPE",
            Var.government to "choose: OLIGARCHY JUNTA ETHNOCRACY",
            Var.age to Var.integer,
            Var.birthday to Var.birthday,
            Var.id to Var.long
        )
    }
}