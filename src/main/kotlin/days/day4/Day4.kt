package days.day4

import days.Day
import util.containsRange
import util.overlaps

class Day4(override val input: String) : Day<Int>(input) {
    override fun solve1(): Int = parseRanges(input).count { it.first containsRange it.second || it.second containsRange it.first }

    override fun solve2(): Int = parseRanges(input).count { it.first overlaps it.second }

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