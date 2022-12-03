package days.day3

import days.Day

object Day3 : Day {
    override fun solve1(input: String): String = input.lines()
        .map { it.subSequence(0 until it.length / 2).toSet().intersect(it.subSequence(it.length / 2 until it.length).toSet()) }
        .sumOf { shared -> shared.sumOf { item -> (item - 'a' + 1).let { if (it < 0) 58 + it else it } } }
        .toString()


    override fun solve2(input: String): String = input.lines().chunked(3)
        .map { Pair(it[0].toList().intersect(it[1].toSet()), it[2]) }
        .map { pair -> pair.first.intersect(pair.second.toSet()) }
        .sumOf { badge -> (badge.first() - 'a' + 1).let { if (it < 0) 58 + it else it } }
        .toString()
}