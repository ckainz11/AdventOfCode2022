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

        for (n in map.getAdjacentCoordinates(current)) {
            val canStep = map[n] <= map[current] + 1
            val shorterPath = shortestPath[n] == 0 || pathToHere < shortestPath[n]
            if (canStep && shorterPath) {
                shortestPath[n] = pathToHere
                if (map[n] == goal)
                    return pathToHere
                lowest = min(lowest, traverse(shortestPath, n, goal))
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