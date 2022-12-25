package days.day25

import days.Day
import kotlin.math.pow

class Day25(override val input: String) : Day<String>(input) {

    override fun solve1(): String = input.lines().sumOf { it.fromSnafu() }.toSnafu()

    override fun solve2(): String = "Advent Of Code 2022 - ckainz11"

    private fun Long.toSnafu(): String {
        return if(this == 0L) ""
        else when(val next = this % 5L) {
            0L, 1L, 2L -> (this / 5L).toSnafu() + next.fromDecimal()
            3L, 4L -> (this / 5L + 1L).toSnafu() + (next - 5L).fromDecimal()
            else -> error("$this is not a valid snafu num")
        }
    }

    private fun snafuToLong(snafu: String): Long =
        snafu.foldIndexed(0L) { index, acc, c -> acc + (c.toDecimal() * 5.0.pow(snafu.length - 1 - index).toLong()) }

    private fun String.fromSnafu(): Long = snafuToLong(this)


    private fun Char.toDecimal(): Long = when (this) {
        '=' -> -2
        '-' -> -1
        else -> digitToInt().toLong()
    }

    private fun Long.fromDecimal(): Char = when(this) {
        -2L -> '='
        -1L -> '-'
        else -> toString()[0]
    }
}