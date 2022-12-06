package days.day6

import days.Day

object Day6 : Day {
    private fun String.findMarker(packetSize: Int): Int = packetSize + windowed(packetSize) {it.toSet()}.indexOfFirst { it.size == packetSize }

    override fun solve1(input: String): String = input.findMarker(4).toString()
    override fun solve2(input: String): String = input.findMarker(14).toString()
}