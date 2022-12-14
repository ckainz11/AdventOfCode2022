package days.day14

import days.Day
import util.Point
import util.lineTo
import kotlin.math.abs
import kotlin.math.sign

class Day14(override val input: String) : Day<Int>(input) {

    private val directions = listOf(Point(0, 1), Point(-1, 1), Point(1, 1))
    private val source = Point(500, 0)
    private val originalRocks = input.generateCave()

    override fun solve1(): Int {
        val occupied = originalRocks.toMutableSet()
        val voidBorder = occupied.sortedByDescending { it.y }[1].y
        var sandCount = 0
        var fellOff = false

        fun dropSand(start: Point): Boolean {
            if (start.y >= voidBorder)
                return true
            for (dir in directions) {
                val next = start + dir
                if (!occupied.contains(next)) {
                    return dropSand(next)
                }
            }
            occupied.add(start)
            sandCount++
            return false
        }

        while (!fellOff) {
            fellOff = dropSand(source)
        }
        return sandCount
    }


    override fun solve2(): Int {
        val occupied = originalRocks.toMutableSet()

        val floor = (occupied.sortedByDescending { it.y }[1] + Point(0, 2)).y
        var sandCount = 0

        fun dropSand(start: Point): Boolean {
            if (occupied.contains(source))
                return true
            for (dir in directions) {
                val next = start + dir
                if (!occupied.contains(next) && next.y < floor) {
                    return dropSand(next)
                }
            }
            occupied.add(start)
            sandCount++
            return false
        }

        var fellOff = false
        while (!fellOff) {
            fellOff = dropSand(source)
        }
        return sandCount
    }

    private fun String.generateCave() = lines().map { line ->
        line.split(" -> ")
            .windowed(2) { (a, b) ->
                a.split(",").let { (x, y) -> Point(x.toInt(), y.toInt()) } lineTo
                b.split(",").let { (x, y) -> Point(x.toInt(), y.toInt()) }
            }.flatten()
    }.flatten().toMutableSet()
}