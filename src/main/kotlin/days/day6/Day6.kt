package days.day6

import days.Day

class Day6(override val input: String) : Day(input) {
    private fun String.findMarker(packetSize: Int): Int = packetSize + windowed(packetSize) {it.toSet()}.indexOfFirst { it.size == packetSize }

    override fun solve1(): String = input.findMarker(4).toString()
    override fun solve2(): String = input.findMarker(14).toString()
}