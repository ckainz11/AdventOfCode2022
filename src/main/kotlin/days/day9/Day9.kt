package days.day9

import days.Day
import util.Point
import util.moveInDirection
import kotlin.math.abs
import kotlin.math.sign

class Day9(override val input: String) : Day(input) {
    private val moves = input.lines().map { it.substringBefore(" ")[0] to it.substringAfter(" ").toInt() }


    override fun solve1(): String = visitedTailPositions(2).toString()

    override fun solve2(): String = visitedTailPositions(10).toString()

    private fun visitedTailPositions(n: Int): Int {
        val knots = MutableList(n) { Point(0, 0) }
        val visited = mutableSetOf<Point>()

        moves.forEach { (dir, steps) ->
            repeat(steps) {
                knots[0] = knots[0] + Point(0, 0).moveInDirection(dir)

                knots.indices.windowed(2) { (head, tail) ->
                    if (!knots[tail].isNeighbourOf(knots[head])) {
                        knots[tail] = knots[tail].moveTo(knots[head])
                    }
                }

                visited.add(knots.last())
            }
        }

        return visited.size
    }

    private fun Point.isNeighbourOf(other: Point) = abs(other.x - x) < 2 && abs(other.y - y) < 2
    private fun Point.moveTo(other: Point) = this + Point((other.x - x).sign, (other.y - y).sign)

}