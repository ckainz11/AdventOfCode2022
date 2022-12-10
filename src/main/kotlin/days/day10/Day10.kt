package days.day10

import days.Day
import javax.lang.model.type.PrimitiveType

class Day10(override val input: String) : Day<String>(input) {
    override fun solve1(): String = executeInstructions(0, ::sumExecutor).toString()

    override fun solve2(): String = "\n\n${executeInstructions("", ::drawExecutor).chunked(40).joinToString("\n")}\n\n"

    private fun <T> executeInstructions(defaultValue: T, executor: (Int, Int, T) -> T): T {
        var result = defaultValue
        var cycleCount = 0
        var x = 1
        for (line in input.lines()) {
            if (line.startsWith("noop")) {
                result = executor(cycleCount, x, result)
                cycleCount++
            } else {
                repeat(2) {
                    result = executor(cycleCount, x, result)
                    cycleCount++
                    if (it == 1) x += line.split(" ")[1].toInt()
                }
            }
        }
        return result
    }

    private fun sumExecutor(cycleCount: Int, x: Int, sum: Int): Int =
        if ((cycleCount + 1) % 40 == 20) sum + ((cycleCount + 1) * x) //off by one reeee
        else sum

    private fun drawExecutor(cycleCount: Int, x: Int, image: String): String =
        image + if (cycleCount % 40 in x - 1..x + 1) '#' else '.'
}