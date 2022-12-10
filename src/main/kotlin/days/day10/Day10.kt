package days.day10

import days.Day

class Day10(override val input: String) : Day<String>(input) {
    override fun solve1(): String {
        var sum = 0
        var cycleCount = 0
        var x = 1

        for (line in input.lines()) {

            if (line.startsWith("noop")) cycleCount++.also { sum += increaseSum(cycleCount, x) }
            else {
                repeat(2) {
                    cycleCount++.also { sum += increaseSum(cycleCount, x) }
                    if (it == 1) x += line.split(" ")[1].toInt()
                }
            }

        }

        return sum.toString()
    }


    private fun increaseSum(cycleCount: Int, x: Int): Int {
        return if (cycleCount == 20 || cycleCount % 40 == 20) (cycleCount * x)
        else 0
    }


    override fun solve2(): String {
        var image = ""
        var cycleCount = 0
        var x = 1
        for (line in input.lines()) {

            if (line.startsWith("noop")) {
                image += drawPixel(cycleCount, x)
                cycleCount++
            } else {
                repeat(2) {
                    image += drawPixel(cycleCount, x)
                    cycleCount++
                    if (it == 1) x += line.split(" ")[1].toInt()
                }
            }
        }
        return "\n\n$image\n\n"
    }

    private fun drawPixel(cycleCount: Int, spritePos: Int): String {
        val pixelPos = cycleCount % 40
        val newLine = if (pixelPos == 0 && cycleCount != 0) "\n" else ""

        for (i in -1..1) {
            if (pixelPos == spritePos + i) {
                return "$newLine#"
            }
        }
        return "$newLine."
    }

}