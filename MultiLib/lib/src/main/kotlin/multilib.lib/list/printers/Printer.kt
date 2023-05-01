package multilib.lib.list.printers

interface Printer {
    fun print(supplier: () -> String) {
        println(supplier())
    }
}