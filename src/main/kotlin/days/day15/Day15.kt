package days.day15

import days.Day
import util.*
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class Day15(override val input: String) : Day<Long>(input) {

    private val part1Target = input.lines()[0].toInt()
    private val sensors = input.lines().drop(1).map { it.allInts() }
        .map { SensorBeacon(Point(it[0], it[1]), Point(it[2], it[3])) }

    override fun solve1(): Long = sensors.mapNotNull { it.getCoverageInRow(part1Target) }.flatten().distinct().size.toLong() -
            sensors.map { it.beacon.y }.filter { it == part1Target }.distinct().size

    override fun solve2(): Long {
        for (y in 0..4000000) {
            val x = findOpenSpot(y)
            if (x != -1L) {
                return x * 4_000_000 + y
            }
        }
        throw RuntimeException("Solution not found")
    }

    private fun findOpenSpot(y: Int): Long {
        val ranges = sensors.mapNotNull { it.getCoverageInRow(y) }.sortedBy { it.first }
        var bigRange = ranges[0]
        if (bigRange.first > 0) {
            return 0
        }

        for (r in ranges) {
            if (bigRange containsRange r) {
                continue
            }

            if (bigRange overlaps r || bigRange adjoint r) {
                bigRange = min(bigRange.first, r.first)..max(bigRange.last, r.last)
            } else {
                return bigRange.last + 1.toLong()
            }
        }

        return -1
    }

    data class SensorBeacon(val sensor: Point, val beacon: Point) {
        fun getCoverageInRow(row: Int): IntRange? {
            val dist = sensor.manhattan(beacon)
            val dy = abs(row - sensor.y)
            return if (dy < dist)
                (dist - dy).let { -it + sensor.x..it + sensor.x }
            else null
        }
    }
}