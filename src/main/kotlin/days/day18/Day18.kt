package days.day18

import days.Day
import util.Point3
import util.allInts

class Day18(override val input: String) : Day<Int>(input) {

    private val cubes = input.lines().map { it.allInts().let { (x, y, z) -> Point3(x, y, z) } }.toSet()

    override fun solve1(): Int = cubes.sumOf { 6 - it.getAdjacents3().count { c -> c in cubes } }

    override fun solve2(): Int {
        val minPoint = Point3(cubes.minOf { it.x }, cubes.minOf { it.y }, cubes.minOf { it.z })
        val maxPoint = Point3(cubes.maxOf { it.x }, cubes.maxOf { it.y }, cubes.maxOf { it.z })

        fun Point3.inBounnds() = x in minPoint.x - 1..maxPoint.x + 1 &&
                y in minPoint.y - 1..maxPoint.y + 1 &&
                z in minPoint.z - 1..maxPoint.z + 1

        val toVisit = mutableListOf(minPoint)
        val visited = mutableSetOf<Point3>()
        var sides = 0
        while (toVisit.isNotEmpty()) {
            val c = toVisit.removeFirst()
            if (c !in visited) {
                c.getAdjacents3()
                    .filter { it.inBounnds() }
                    .forEach { if (it in cubes) sides++ else toVisit += it }
                visited += c
            }
        }
        return sides
    }

    private val offsets = listOf(-1, 1)
    private fun Point3.getAdjacents3(): List<Point3> = buildList {
        offsets.forEach {
            add(Point3(x + it, y, z))
            add(Point3(x, y + it, z))
            add(Point3(x, y, z + it))
        }
    }


}