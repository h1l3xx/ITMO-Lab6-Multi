package multilib.app.commands.tools

class Result(private val message: String, private val bool: Boolean) {
    fun getBool(): Boolean {
        return this.bool
    }
    fun getMessage(): String{
        return this.message
    }
}