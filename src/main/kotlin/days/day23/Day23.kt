package days.day23

import days.Day
import util.*
import kotlin.math.abs

class Day23(override val input: String) : Day<Int>(input) {

    private val occupied = emptyMatrixOf(500, 500, false).toMutableMatrix()
    private val elves = buildSet {
        input.lines().forEachIndexed {y, s ->
            s.forEachIndexed {x, c -> if(c == '#') add(Point(250 + x, 250 + y)) }
        }
    }.toMutableSet().onEach { occupied[it] = true }

    override fun solve1(): Int {
        repeat(10) { simulate(it) }
        return elves.emptyTiles()
    }

    override fun solve2(): Int {
        var startDir = 10
        while (simulate(startDir)) { startDir++ }
        return startDir + 1
    }

    private fun simulate(startDir: Int): Boolean {
        val toFrom = mutableMapOf<Point, Point>()
        for (elf in elves) {
            val neighbors = elf.getAdjacents()
            if (neighbors.any { occupied[it] }) {
                for (dir in 0 until 4) {
                    val directionalNeighbors = directions[(startDir + dir) % 4].map { neighbors[it] }
                    if (directionalNeighbors.none { occupied[it] }) {
                        val destination = directionalNeighbors[1]
                        if (destination in toFrom) toFrom[destination] = Point(-1, -1)
                        else toFrom[destination] = elf
                        break
                    }
                }
            }
        }

        for ((key, value) in toFrom) {
            if (value == Point(-1, -1)) continue
            elves.add(key)
            occupied[key] = true
            elves.remove(value)
            occupied[value] = false
        }
        return toFrom.isNotEmpty()
    }


    private val adjacents = listOf(
        Point(-1, -1), Point(0, -1), Point(1, -1),
        Point(1, 0), Point(1, 1),
        Point(0, 1), Point(-1, 1),
        Point(-1, 0)
    )

    private val north = listOf(0, 1, 2)
    private val east = listOf(2, 3, 4)
    private val south = listOf(4, 5, 6)
    private val west = listOf(6, 7, 0)
    private val directions = listOf(north, south, west, east)

    private fun Point.getAdjacents() = adjacents.map { this + it }
    private fun Set<Point>.emptyTiles(): Int =
        (abs(this.maxOf { it.x } - this.minOf { it.x }) + 1) * (abs(this.maxOf { it.y } - this.minOf { it.y }) + 1) - this.size
}