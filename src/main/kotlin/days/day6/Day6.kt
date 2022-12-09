package days.day6

import days.Day

class Day6(override val input: String) : Day<Int>(input) {
    private fun String.findMarker(packetSize: Int): Int = packetSize + windowed(packetSize) {it.toSet()}.indexOfFirst { it.size == packetSize }

    override fun solve1(): Int = input.findMarker(4)
    override fun solve2(): Int = input.findMarker(14)
}