import common.InputReader
import kotlinx.coroutines.runBlocking

class Day02 {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) = runBlocking {
            val commands = InputReader.readInput("day/2/input")
                .split(System.lineSeparator())
                .map { it.split(" ") }
                .filter { it.size == 2 }
                .map { Pair(it[0], it[1]) }
                .toList()

            var position = 0
            var depth = 0
            commands
                .onEach {
                    if (it.first == "forward") {
                        position += it.second.toInt()
                    } else {
                        depth += if (it.first == "down") it.second.toInt() else it.second.toInt() * -1
                    }
                }

            println(position * depth)

            position = 0
            depth = 0
            var aim = 0
            commands
                .onEach {
                    if (it.first == "forward") {
                        position += it.second.toInt()
                        depth += aim * it.second.toInt()
                    } else {
                        if (it.first == "down") {
                            aim += it.second.toInt()
                        } else {
                            aim += it.second.toInt() * -1
                        }
                    }
                }

            println(position * depth)
        }
    }
}   