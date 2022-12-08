package days.day8

import days.Day
import util.*

class Day8(override val input: String) : Day(input) {
    private var forest = matrixOf(input.lines().map { row -> row.toList().map(Char::digitToInt) })

    override fun solve1(): String = forest.mapMatrixIndexed { i, j, tree -> forest.getRangesToEdge(i, j).any { it.isEmpty() || it.all { t -> t < tree } } }
        .count { it }.toString()

    override fun solve2(): String = forest.mapMatrixIndexed { i, j, tree -> calcViewScore(i, j, tree) }.matrixMax().toString()

    private fun calcViewScore(row: Int, col: Int, tree: Int): Int = listOf('N', 'E', 'S', 'W').map { look(Point(col, row), it, tree) }.reduce(Int::times)

    private fun look(point: Point, direction: Char, tree: Int): Int {
        val next = point.moveInDirection(direction)
        return try {
            if (forest[next.y][next.x] < tree)
                1 + look(next, direction, tree)
            else 1
        } catch (ex: IndexOutOfBoundsException) {
            0
        }

    }
}