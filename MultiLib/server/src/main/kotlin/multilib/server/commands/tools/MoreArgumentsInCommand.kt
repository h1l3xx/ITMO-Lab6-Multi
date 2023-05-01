package multilib.app.commands.tools

class MoreArgumentsInCommand {
    fun moreArguments(arguments : List<String>, value : String): HashMap<String, Any> {
        val arg : HashMap<String, Any> = HashMap()
        var key = 1
        for (element in arguments){
            arg[key.toString()] = element
            key += 1
        }
        arg[value] = key-1
        return arg
    }
}