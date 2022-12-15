package days.day15

import days.Day
import util.Point
import util.allInts
import util.containsRange
import util.overlaps
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class Day15(override val input: String) : Day<Long>(input) {

    private val part1Target = input.lines()[0].toInt()
    private val sensors = input.lines().drop(1).map { it.removePrefix("Sensor at ").allInts() }
        .map { Point(it[0], it[1]) to Point(it[2], it[3]) }

    private val sensorDist = sensors.map { (it.first - it.second).let { diff -> it.first to abs(diff.x) + abs(diff.y) } }

    override fun solve1(): Long = getFilledPositionsInRow(part1Target).flatten().distinct().size.toLong() -
        sensors.map { it.second.y }.filter { it == part1Target }.distinct().size


    private fun getFilledPositionsInRow(row: Int) = buildSet {
        sensorDist.forEach { (s, dist) ->
            val dy = abs(row - s.y)
            if (dy < dist) {
                val dx = dist - dy
                add(-dx + s.x..dx + s.x)
            }
        }
    }

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
        val ranges = getFilledPositionsInRow(y).sortedBy { it.first }
        var bigRange = ranges[0]
        if (bigRange.first > 0) {
            return 0
        }

        for (r in ranges) {
            if (bigRange containsRange r) {
                continue
            }

            if (bigRange overlaps r || bigRange.last + 1 == r.first) {
                bigRange = min(bigRange.first, r.first)..max(bigRange.last, r.last)
            } else {
                return bigRange.last + 1.toLong()
            }
        }

        return -1
    }
}