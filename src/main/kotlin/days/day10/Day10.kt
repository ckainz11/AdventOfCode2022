package days.day10

import days.Day

class Day10(override val input: String) : Day<String>(input) {

    private val fill = '#' //recommended: '█'
    private val space = '.' //recommended: ' '
    override fun solve1(): String = executeInstructions(0)
        { cycles, x, sum -> sum + if ((cycles + 1) % 40 == 20) (cycles + 1) * x else 0 }
        .toString()

    override fun solve2(): String = "\n\n${
        executeInstructions("") { cycles, x, image -> image + if (cycles % 40 in x - 1..x + 1) fill else space }
        .chunked(40).joinToString("\n")
        }\n\n"

    private fun <T> executeInstructions(defaultValue: T, executor: (Int, Int, T) -> T): T {
        var cycleCount = 0
        var x = 1

        fun noop(startVal: T, n: Int = 1): T =
            (1..n).fold(startVal) { acc, _ -> executor(cycleCount, x, acc).also { cycleCount++ } }

        return input.lines().fold(defaultValue) { acc, line ->
            if (line.startsWith("noop")) noop(acc)
            else noop(acc, 2).also { x += line.split(" ")[1].toInt() }
        }
    }
}