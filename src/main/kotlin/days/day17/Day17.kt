package days.day17

import days.Day
import util.*

class Day17(override val input: String) : Day<Long>(input) {


    private val hline = listOf(Point(0, 0), Point(1, 0), Point(2, 0), Point(3, 0))
    private val vline = listOf(Point(0, 0), Point(0, 1), Point(0, 2), Point(0, 3))
    private val box = listOf(Point(0, 0), Point(1, 0), Point(0, 1), Point(1, 1))
    private val ell = listOf(Point(0, 0), Point(1, 0), Point(2, 0), Point(2, 1), Point(2, 2))
    private val cross = listOf(Point(0, 1), Point(1, 0), Point(1, 1), Point(2, 1), Point(1, 2))

    private val shapes = listOf(hline, cross, ell, vline, box)

    override fun solve1(): Long {
        var time = 0
        val grid = emptyMatrixOf(10_000, 7, false)
        var highestPoint = -1

        repeat(2022) { nextShape ->

            val spawn = Point(2, highestPoint + 4)
            var shape = shapes[nextShape % shapes.size].map { it + spawn }.toList()

            while (true) {

                if (input[time % input.length] == '<') {
                    if (isFree(shape, Point.LEFT, grid))
                        shape = shape.map { it + Point.LEFT }
                } else {
                    if(isFree(shape, Point.RIGHT, grid))
                        shape = shape.map { it + Point.RIGHT }
                }
                time++

                if(isFree(shape, Point.DOWN, grid))
                    shape = shape.map { it + Point.DOWN }
                else
                    break
            }

            shape.forEach { grid[it] = true }
            highestPoint = grid.indexOfLast { it.contains(true) }
        }

        return grid.indexOfLast { it.contains(true) }.toLong() + 1
    }

    private fun isFree(shape: List<Point>, offset: Point, grid: Matrix<Boolean>): Boolean = shape.all { s ->
        (s + offset).let {
            (it.x in grid[0].indices) && (it.y in grid.indices) && !grid[it]
        }
    }
    //Credit: @ActiveNerd
    override fun solve2(): Long {
        val windCycle = input.length
        val shapeCycle = shapes.size

        val seenCycles = mutableSetOf<Int>()
        var cycleSyncNum: Int? = null

        val partialCycleHeights = mutableListOf<Int>()
        var ogPoint: Point? = null
        var ogRockNum = -1

        val grid = MutableList(100_000) { MutableList(7) { false } }
        var rockCount = 0
        var time = 0
        var highestSpot = -1

        repeat(100000) {
            // Simulate rock
            val spawn = Point(2, highestSpot + 4)
            var shape = shapes[rockCount % shapes.size].map { it + spawn }.toList()
            // Spawn

            while (true) {
                // Simulate left / right
                val goLeft = input[time % input.length] == '<'
                time++
                if (goLeft) {
                    if (isFree(shape, Point.LEFT, grid)) {
                        shape = shape.map { it + Point.LEFT }
                    }
                } else if (isFree(shape, Point.RIGHT, grid)) {
                    shape = shape.map { it + Point.RIGHT }
                }

                if (isFree(shape, Point.DOWN, grid)) {
                    shape = shape.map { it + Point.DOWN }
                } else {
                    break
                }
            }

            shape.forEach { grid[it] = true }
            highestSpot = grid.indexOfLast { it.contains(true) }

            if (rockCount % shapes.size == (shapeCycle - 1)) {
                if (cycleSyncNum != null && time % windCycle == cycleSyncNum) {
                    val cycleHeight = shape[0].y - ogPoint!!.y
                    val cycleLength = rockCount - ogRockNum

                    val rocksToJumpOver = 1000000000000 - ogRockNum
                    val cyclesToJump = rocksToJumpOver / cycleLength
                    val extraRocks = (rocksToJumpOver % cycleLength).toInt()

                    return (cyclesToJump * cycleHeight) + partialCycleHeights[extraRocks]
                } else if (cycleSyncNum == null) {
                    val windNum = time % windCycle
                    if (!seenCycles.add(windNum)) {
                        cycleSyncNum = windNum
                        ogPoint = shape[0].copy()
                        ogRockNum = rockCount
                    }
                }
            }

            if (ogPoint != null) {
                partialCycleHeights.add(highestSpot)
            }

            rockCount += 1
        }

        return -1
    }
}