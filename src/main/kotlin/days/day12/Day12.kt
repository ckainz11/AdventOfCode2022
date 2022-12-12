package days.day12

import days.Day
import util.*
import kotlin.math.min

class Day12(override val input: String) : Day<Int>(input) {

    private val map: Matrix<Int> = input.lines().map { row -> row.toList().map { it.heightValue() } }
    private val end = findEnd()
    override fun solve1(): Int = traverse(emptyMatrixOf(map.getRowNum(), map.getColNum(), 0), end, 27)

    override fun solve2(): Int = traverse(emptyMatrixOf(map.getRowNum(), map.getColNum(), 0), end, 26)

    private fun findEnd(): Point {
        for ((y, row) in map.withIndex()) {
            for ((x, col) in row.withIndex()) {
                if (col == 0) {
                    return Point(x, y)
                }
            }
        }
        return Point(0, 0)
    }

    private fun traverse(shortestPath: MutableMatrix<Int>, current: Point, goal: Int): Int {
        val pathToHere = shortestPath[current] + 1
        var lowest = Int.MAX_VALUE

        map.getAdjacentCoordinates(current).forEach {
            val canStep = map[it] <= map[current] + 1
            val shorterPath = shortestPath[it].let { p -> p == 0 || pathToHere < p}
            if (canStep && shorterPath) {
                shortestPath[it] = pathToHere
                if (map[it] == goal)
                    return pathToHere
                lowest = min(lowest, traverse(shortestPath, it, goal))
            }

        }
        return lowest
    }

    private fun Char.heightValue(): Int = when (this) {
        'E' -> 0
        'S' -> 27
        else -> 'z' - this + 1
    }
}