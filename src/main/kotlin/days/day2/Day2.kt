package days.day2

import days.Day

class Day2(override val input: String): Day<Int>(input) {
    override fun solve1(): Int = input.lines().sumOf { roundScore(it) }

    private fun roundScore(round: String): Int {
        return when(round) {
            "A X" -> 3 + 1
            "A Y" -> 6 + 2
            "A Z" -> 0 + 3
            "B X" -> 0 + 1
            "B Y" -> 3 + 2
            "B Z" -> 6 + 3
            "C X" -> 6 + 1
            "C Y" -> 0 + 2
            "C Z" -> 3 + 3
            else -> throw Exception("not a valid round")
        }
    }

    override fun solve2(): Int = input.lines().sumOf { roundScore2(it) }

    private fun roundScore2(round: String): Int {
        return when(round) {
            "A X" -> 0 + 3
            "A Y" -> 3 + 1
            "A Z" -> 6 + 2
            "B X" -> 0 + 1
            "B Y" -> 3 + 2
            "B Z" -> 6 + 3
            "C X" -> 0 + 2
            "C Y" -> 3 + 3
            "C Z" -> 6 + 1
            else -> throw Exception("not a valid round")
        }
    }
}