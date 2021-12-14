import common.InputReader
import kotlinx.coroutines.runBlocking

class Day01 {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) = runBlocking {
            val depthMeasurements = InputReader.readInput("day/1/input")
                .split(System.lineSeparator())
                .map { it.toIntOrNull() }
                .filterNotNull()
                .toList()
            println(depthMeasurements
                .mapIndexed { index, current ->
                    when (index) {
                        0 -> 0
                        else -> if (current > depthMeasurements[index - 1]) 1 else 0
                    }
                }
                .sum())
            println(
                depthMeasurements
                    .mapIndexed { index, current ->
                        if (index > 0 && index + 2 < depthMeasurements.size) {
                            println("index $index")
                            println (index.rangeTo(index + 2)
                                .sumOf { depthMeasurements[it] })
                            println ((index - 1).rangeTo((index - 1) + 2)
                                .sumOf { depthMeasurements[it] })
                            if (index.rangeTo(index + 2)
                                    .sumOf { depthMeasurements[it] } > (index - 1).rangeTo((index - 1) + 2)
                                    .sumOf { depthMeasurements[it] }
                            ) 1 else 0
                        } else {
                            0
                        }
                    }
                    .sum()
            )
        }
    }
}   