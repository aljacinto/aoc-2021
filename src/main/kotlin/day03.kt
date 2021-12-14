import common.InputReader
import kotlinx.coroutines.runBlocking

class Day03 {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) = runBlocking {
            val report = InputReader.readInput("day/3/input")
                .split(System.lineSeparator())
                .filter { it.isNotBlank() }
                .map { it.toCharArray() }

            println(powerConsumption(report))
            println(
                rating(report, 0) { bitCriteria -> if (bitCriteria.first > bitCriteria.second) '0' else '1' } *
                        rating(report, 0) { bitCriteria -> if (bitCriteria.first <= bitCriteria.second) '0' else '1' }
            )
        }

        private fun powerConsumption(report: List<CharArray>): Int {
            val generatedReport = report
                .fold("100101001000".toCharArray().map { Pair(0, 0) }.toList()) { acc, chars ->
                    chars.mapIndexed { index, c ->
                        Pair(
                            if (c == '0') acc[index].first + 1 else acc[index].first,
                            if (c == '1') acc[index].second + 1 else acc[index].second
                        )
                    }.toList()
                }
            val epsilonRate = generatedReport.map { if (it.first > it.second) '0' else '1' }.toCharArray()
            val gammaRate = epsilonRate.map { if (it == '0') '1' else '0' }.toCharArray()

            return String(epsilonRate).toInt(2) * String(gammaRate).toInt(2)
        }

        private fun rating(
            report: List<CharArray>,
            bitPosition: Int,
            criteria: (pair: Pair<Int, Int>) -> Char
        ): Int {
            val bitCriteria = report
                .fold(Pair(0, 0)) { acc, chars ->
                    Pair(
                        if (chars[bitPosition] == '0') acc.first + 1 else acc.first,
                        if (chars[bitPosition] == '1') acc.second + 1 else acc.second
                    )
                }.let { criteria(it) }

            val filteredReport = report
                .filter { chars -> bitCriteria == chars[bitPosition] }
            return if (filteredReport.size == 1) {
                String(filteredReport.first()).toInt(2)
            } else {
                rating(filteredReport, bitPosition + 1, criteria)
            }
        }
    }
}