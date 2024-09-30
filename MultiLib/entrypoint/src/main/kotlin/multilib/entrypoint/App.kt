package multilib.entrypoint

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


val entryPoint = EntryPoint()
fun main() : Unit = runBlocking {
    launch { entryPoint.start().join() }
}