package days.day1

import days.Day

object Day1 : Day {
    override fun solve1(input: String): String  = input.split("\r\n\r\n").map { elves -> elves.lines().sumOf { it.toInt() } }.maxOf { calories -> calories }.toString()

    override fun solve2(input: String): String = input.split("\r\n\r\n").map { elves -> elves.lines().sumOf { it.toInt() } }.sortedDescending().take(3).sum().toString()
}

