import common.InputReader
import kotlinx.coroutines.runBlocking

class Day04 {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            runBlocking {
                val inputLines = InputReader.readInput("day/4/input")
                    .split("\n\n")
                    .filter { it.isNotBlank() }

                val drawNumbers = inputLines.get(0).split(",").map(String::toInt)

                val bingoCards = inputLines
                    .drop(1)
                    .filter { it.isNotBlank() }
                    .map { BingoCard(it) }

                val firstWinner = drawNumbers.asSequence()
                    .flatMap { drawnNumber -> bingoCards.map { it.markNumber(drawnNumber) } }
                    .first { it.isWinner() }

                println(firstWinner.unmarkedNumbers() * firstWinner.lastNumber)

                val bingoCards2 = inputLines
                    .drop(1)
                    .filter { it.isNotBlank() }
                    .map { BingoCard(it) }

                val lastCard = drawNumbers.asSequence()
                    .map { drawnNumber ->
                        val nonWinners = bingoCards2.filter { !it.isWinner() }
                        val remaining = nonWinners.map { it.markNumber(drawnNumber) }
                            .filter { it.isWinner() }
                        if (nonWinners.size == 1 && remaining.size == 1) {
                            nonWinners
                        } else {
                            bingoCards2
                        }
                    }
                    .filter { it.size == 1 }
                    .first()
                    .first()

                println(lastCard.unmarkedNumbers() * lastCard.lastNumber)
            }
        }
    }
}

class BingoCard(val numberRows: String) {
    var lastNumber = 0
    private var card = numberRows.split("\n")
        .filter { it.isNotBlank() }
        .map {
            it.split("\\s+".toRegex())
                .filter(String::isNotBlank)
                .map {
                    Pair(it.toInt(), false)
                }
        }

    fun markNumber(number: Int): BingoCard {
        lastNumber = number
        card = card
            .map {
                it.map {
                    Pair(it.first, if (it.second) true else it.first == number)
                }
            }
        return this
    }

    fun isWinner() = if (card.any { row -> row.all { it.second } }) {
        true
    } else {
        IntRange(0, card.first().size - 1).any { row -> card.all { it[row].second } }
    }

    override fun toString(): String {
        return "--------------------\n" + card
            .map { row ->
                row.map {
                    if (it.second) ("*" + it.first.toString()).padStart(3) else it.first.toString().padStart(3)
                }.joinToString(" ")
            }
            .joinToString("\n") + "\n--------------------\n"
    }

    fun unmarkedNumbers() = card.sumOf { row -> row.sumOf { if(it.second) 0 else it.first } }
}

