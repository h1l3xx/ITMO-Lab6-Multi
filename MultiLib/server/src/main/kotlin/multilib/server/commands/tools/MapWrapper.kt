package multilib.app.commands.tools

data class MapWrapper(
    val name : String? = null,
    val fieldType: FieldType? = null,
    val min : Int? = null,
    val max : Int? = null,
    val chooseable : Boolean? = null,
    val choose : String? = null
){
}