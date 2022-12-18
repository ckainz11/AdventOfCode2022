package days.day18

import days.Day
import util.Point3
import util.allInts

class Day18(override val input: String) : Day<Int>(input) {

    private val cubes = input.lines().map { it.allInts().let { (x, y, z) -> Point3(x, y, z) } }.toSet()

    override fun solve1(): Int = cubes.sumOf { 6 - it.neighbors().count { c -> c in cubes } }

    override fun solve2(): Int {
        val minPoint = Point3(cubes.minOf { it.x - 1 }, cubes.minOf { it.y - 1 }, cubes.minOf { it.z - 1 })
        val maxPoint = Point3(cubes.maxOf { it.x + 1 }, cubes.maxOf { it.y + 1 }, cubes.maxOf { it.z + 1 })

        fun Point3.inBounds() = x in minPoint.x..maxPoint.x &&
                y in minPoint.y..maxPoint.y &&
                z in minPoint.z..maxPoint.z

        val toVisit = mutableListOf(minPoint)
        val visited = mutableSetOf<Point3>()
        var sides = 0
        while (toVisit.isNotEmpty()) {
            val c = toVisit.removeFirst()
            if (c !in visited) {
                c.neighbors()
                    .filter { it.inBounds() }
                    .forEach { if (it in cubes) sides++ else toVisit += it }
                visited += c
            }
        }
        return sides
    }
}