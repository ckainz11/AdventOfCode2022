package days.day10

import days.Day

class Day10(override val input: String) : Day<String>(input) {

    private val fill = '#' //recommended: '█'
    private val space = '.' //recommended: ' '
    override fun solve1(): String = executeInstructions(0, ::sumExecutor).toString()

    override fun solve2(): String = "\n\n${executeInstructions("", ::drawExecutor).chunked(40).joinToString("\n")}\n\n"

    private fun <T> executeInstructions(defaultValue: T, executor: (Int, Int, T) -> T): T {
        var cycleCount = 0
        var x = 1

        fun noop(n: Int = 1, startVal: T): T = (1 .. n).fold(startVal) { acc, _  -> executor(cycleCount, x, acc).also {cycleCount++} }

        return input.lines().fold(defaultValue) { acc, s ->
            if (s.startsWith("noop"))
                noop(1, acc)
            else
                noop(2, acc).also { x += s.split(" ")[1].toInt() }
        }
    }

    private fun sumExecutor(cycleCount: Int, x: Int, sum: Int): Int =
        sum + if ((cycleCount + 1) % 40 == 20) (cycleCount + 1) * x else 0 //off by one reeee

    private fun drawExecutor(cycleCount: Int, x: Int, image: String): String =
        image + if (cycleCount % 40 in x - 1..x + 1) fill else space
}