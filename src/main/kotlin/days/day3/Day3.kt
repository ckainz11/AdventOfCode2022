package days.day3

import days.Day

object Day3 : Day {
    override fun solve1(input: String): String = input.lines()
        .map { it.chunked(it.length / 2) }
        .sumOf { it.sharedItem().priority() }
        .toString()


    override fun solve2(input: String): String = input.lines().chunked(3)
        .sumOf { it.sharedItem().priority() }
        .toString()
    private fun Char.priority(): Int = 1 + if (isUpperCase()) this - 'A' + 26 else this - 'a'
    private fun List<String>.sharedItem(): Char = map { it.toSet() }.reduce { left, right -> left intersect right}.first()

}