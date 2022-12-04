package days.day4

import days.Day
import util.containsRange
import util.overlaps

object Day4 : Day {
    override fun solve1(input: String): String = parseRanges(input)
        .count { it.first containsRange it.second || it.second containsRange it.first }
        .toString()


    override fun solve2(input: String): String = parseRanges(input)
        .count { it.first overlaps it.second }
        .toString()

    private fun parseRanges(input: String) = input.lines().map { line ->
        line.split(",")
            .let { (left, right) ->
                Pair(
                    left.split("-").let { (first, last) -> first.toInt()..last.toInt() },
                    right.split("-").let { (first, last) -> first.toInt()..last.toInt() }
                )
            }
    }

}