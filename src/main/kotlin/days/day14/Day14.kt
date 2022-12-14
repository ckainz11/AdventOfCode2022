package days.day14

import days.Day
import util.Point
import kotlin.math.abs
import kotlin.math.sign

class Day14(override val input: String) : Day<Int>(input) {

    private val directions = listOf(Point(0,1), Point(-1, 1), Point(1,1))
    private val sandOirigin = Point(500,0)
    private val originalRocks = input.genrateCave()

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
            fellOff = dropSand(sandOirigin)
        }
        return sandCount
    }


    override fun solve2(): Int {
        val occupied = originalRocks.toMutableSet()

        val floor = (occupied.sortedByDescending { it.y }[1] + Point(0,2)).y
        var sandCount = 0

        fun dropSand(start: Point): Boolean {
            if(occupied.contains(sandOirigin))
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
            fellOff = dropSand(sandOirigin)
        }
        return sandCount
    }

    private fun generateRocks(start: Point, end: Point) = buildList {
        val xR = (end.x - start.x).let { Pair(it.sign, 0..abs(it)) }
        val yR = (end.y - start.y).let { Pair(it.sign, 0..abs(it)) }
        for (x in xR.second) {
            for (y in yR.second) {
                add(start + Point(x * xR.first, y * yR.first))
            }
        }
    }
    private fun String.genrateCave() = lines().map { line ->
            line.split(" -> ")
                .windowed(2) { (a, b) ->
                    generateRocks(
                        a.split(",").let { (x, y) -> Point(x.toInt(), y.toInt()) },
                        b.split(",").let { (x, y) -> Point(x.toInt(), y.toInt()) }
                    )
                }.flatten()
        }.flatten().toMutableSet()
}