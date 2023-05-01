package multilib.client.handkers

class Scanner : Reader() {
    override fun readLine(): String?{
        return readlnOrNull()
    }
}