package days.day20

import days.Day

class Day20(override val input: String) : Day<Long>(input) {

    private val nums = input.lines().map { it.toInt() }

    override fun solve1(): Long = decrypt()

    override fun solve2(): Long = decrypt(811589153L, 10)

    private val coords = listOf(1000, 2000, 3000)

    private fun decrypt(key: Long = 1, times: Int = 1): Long =
        nums.mapIndexed { index, i -> index to i * key }.let { it to it.toMutableList() }
            .let { (original, moved) ->
                (1..times).fold(mutableListOf<Pair<Int, Long>>()) { _, _ ->
                    original.forEach { p ->
                        moved.indexOf(p).also { moved.removeAt(it); moved.add((it + p.second).mod(moved.size), p) }
                    }.let { moved }
                }.let {
                    moved.indexOfFirst { it.second == 0L }.let { coords.sumOf { c -> moved[(it + c) % nums.size].second } }
                }
            }
}