package days.day3

import days.Day

object Day3 : Day {
    override fun solve1(input: String): String = input.lines()
        .map { it.subSequence(0 until it.length / 2).toSet().intersect(it.subSequence(it.length / 2 until it.length).toSet()) }
        .sumOf { shared -> shared.sumOf { item -> item.priority() } }
        .toString()


    override fun solve2(input: String): String = input.lines().chunked(3)
        .map { Pair(it[0].toSet().intersect(it[1].toSet()), it[2]) }
        .map { it.first.intersect(it.second.toSet()) }
        .sumOf { it.first().priority() }
        .toString()
    private fun Char.priority(): Int = 1 + if (isUpperCase()) this - 'A' + 26 else this - 'a'

}