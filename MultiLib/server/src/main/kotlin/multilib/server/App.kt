/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package multilib.server

import multilib.app.CommandManager
import multilib.app.Operator
import multilib.app.city.CityCollection
import multilib.app.senders.USender
import multilib.app.commands.*
import multilib.server.server.UpdServer
import multilib.server.commands.Long
import multilib.server.commands.users.Authorization
import multilib.server.commands.users.SingUp


val collection = CityCollection()
val operator = Operator()
val commandManager = CommandManager()
val uSender = USender()


fun main(){
    commandManager.register(Add(), Clear(), ExecuteScript(), Exit(), FilterContainsName(), Help(), Info(),
        PrintAscending(), RemoveAllByMetersAboveSeaLevel(), RemoveAt(), RemoveById(), RemoveLower(), Show(), Sort(), UpdateById(), Long(),
        Authorization(), SingUp()
    )
    UpdServer().run()
}