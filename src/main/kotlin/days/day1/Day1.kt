package days.day1

import days.Day

class Day1(override var input: String) : Day(input) {
    override fun solve1(): String =
        input.split("\r\n\r\n").map { elves -> elves.lines().sumOf { it.toInt() } }.maxOf { calories -> calories }
            .toString()

    override fun solve2(): String =
        input.split("\r\n\r\n").map { elves -> elves.lines().sumOf { it.toInt() } }.sortedDescending().take(3).sum()
            .toString()
}

