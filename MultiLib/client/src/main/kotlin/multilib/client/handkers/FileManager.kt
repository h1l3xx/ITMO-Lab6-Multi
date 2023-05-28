package multilib.client.handkers



import multilib.client.commands.CheckScript
import multilib.client.commands.Var
import java.io.BufferedReader
import java.io.FileReader


class FileManager {
    private var file = emptyList<String>()
    fun file(wayToFile : String) : List<String> {
        file = emptyList()
        return if (workWithFile(wayToFile)){
            getFile()
        } else {
            emptyList()
        }
    }
    private fun workWithFile(wayToFile : String) : Boolean {
        var mainFile = """"""
        var lines = 0
        val checker = CheckScript()

        try{
            val bufferReader = BufferedReader(FileReader(wayToFile))
            while (bufferReader.readLine() != null){
                lines += 1
            }
            val buf = BufferedReader(FileReader(wayToFile))
            while (lines > 0){
                mainFile += buf.readLine() + "\n"
                lines -= 1
            }
            val commands = mainFile.lines()
            if (checker.check("${Var.exec} $wayToFile")){
                val numbersOfCommands = commands.size
                var counter = 0
                while (counter != numbersOfCommands){
                    if (commands[counter].contains(Var.exec)){
                        if (!checker.check(commands[counter])){
                            return false
                        }
                    }
                    counter += 1
                }
            }
            setFile(collapse(mainFile))

            return true
        }catch (e : Exception){
            println(e.printStackTrace())
        }
        return true
    }
    private fun setFile(files : String){
        this.file = emptyList()
        this.file = files.lines().dropLast(1)
    }
    private fun getFile(): List<String> {
        return this.file
    }
    private fun collapse(file : String) : String {
        val allCommands = file.lines()
        var returnFile = ""
        var collapse = ""
        var counter = 0
        var checker = false
        for (i in 0 until allCommands.size){
            if (allCommands[i].contains(Var.add) || checker){
                collapse += allCommands[i] + " "
                counter += 1
                if (counter == 11){
                    returnFile += collapse
                    checker = false
                }else{
                    checker = true
                }
            }else{
                returnFile += allCommands[i] + "\n"
            }
        }
        return returnFile
    }
}