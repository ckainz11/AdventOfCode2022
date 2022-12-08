package days.day8

import days.Day
import util.*

class Day8(override var input: String) : Day(input) {
    private lateinit var forest: Matrix<Int>
    override fun solve1(): String {
        forest = matrixOf(input.lines().map { row -> row.toList().map(Char::digitToInt) })
        return forest.mapMatrixIndexed { i, j, tree -> isVisible(i, j, tree) }.count { it }.toString()
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
        return directions.map { look(tree, row, col, it) }.reduce(Int::times)
    }

    private fun look(tree: Int, row: Int, col: Int, direction: Char): Int {
        val next = Point(col, row).moveInDirection(direction)
        return try {
            if (forest[next.y][next.x] < tree)
                1 + look(tree, next.y, next.x, direction)
            else 1
        } catch (ex: IndexOutOfBoundsException) {
            0
        }

    }
}