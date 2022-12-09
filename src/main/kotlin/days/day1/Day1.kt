package days.day1

import days.Day

class Day1(override val input: String) : Day<Int>(input) {
    override fun solve1(): Int = input.split("\n\n")
        .map { elves -> elves.lines().sumOf { it.toInt() } }.maxOf { calories -> calories }

    override fun solve2(): Int = input.split("\n\n")
        .map { elves -> elves.lines().sumOf { it.toInt() } }.sortedDescending().take(3).sum()
}

