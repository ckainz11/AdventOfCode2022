package days.day8

import days.Day
import util.*

class Day8(override var input: String) : Day(input) {
    private lateinit var forest: Matrix<Int>
    override fun solve1(): String {
        forest = matrixOf(input.lines().map { row -> row.toList().map(Char::digitToInt) })
        return forest.mapMatrixIndexed { i, j, tree -> forest.getRangesToEdge(i, j).any { it.isEmpty() || it.all { t -> t < tree } } }
            .count { it }.toString()
    }

    override fun solve2(): String =
        forest.mapMatrixIndexed { i, j, tree -> calcViewScore(i, j, tree) }.matrixMax().toString()

    private fun isVisible(i: Int, j: Int, tree: Int): Boolean {
        val row = forest[i]
        val col = forest.getColumn(j)
        val directions = listOf(
            col.subList(i + 1, col.size),
            col.subList(0, i),
            row.subList(j + 1, row.size),
            row.subList(0, j)
        )
        return directions.any { it.isEmpty() || it.all { other -> other < tree } }
    }

    private fun calcViewScore(row: Int, col: Int, tree: Int): Int {
        val directions = listOf('N', 'E', 'S', 'W')
        return directions.map { look(Point(row, col), it, tree) }.reduce(Int::times)
    }

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