package days.day24

import days.Day
import util.Point
import java.util.PriorityQueue

class Day24(override val input: String) : Day<Int>(input) {

    private val lines = input.lines()
    private val start = Point(lines.first().indexOf('.'), 0)
    private val end = Point(lines.last().lastIndexOf('.'), lines.lastIndex)

    private fun isFree(p: Point, time: Int): Boolean = p.let { (x, y) ->
        if (x <= 0 || y <= 0 || y >= lines.lastIndex || x >= lines.getOrElse(y) { "" }.lastIndex) {
            lines.getOrNull(y)?.getOrNull(x) == '.'
        } else {
            lines[y][(x - 1 + time).mod(lines[y].length - 2) + 1] != '<' &&
                    lines[y][(x - 1 - time).mod(lines[y].length - 2) + 1] != '>' &&
                    lines[(y - 1 + time).mod(lines.size - 2) + 1][x] != '^' &&
                    lines[(y - 1 - time).mod(lines.size - 2) + 1][x] != 'v'
        }
    }

    private val actions = Point.directions + listOf(Point(0, 0))
    private fun search(start: Point, end: Point, startTime: Int = 0): Int {
        val visited = mutableSetOf(Pair(startTime, start))
        val toVisit = PriorityQueue(compareBy(Node::h))
        toVisit.add(Node(0, startTime, start))
        while (toVisit.isNotEmpty()) {
            val (h, time, pos) = toVisit.remove()
            if (pos == end) return time + 1
            for (action in actions) {
                val target = action + pos
                if (!isFree(pos, time + 1)) continue
                val state = Pair(time + 1, target)
                if (visited.add(state)) toVisit.add(Node(time + target.mDist(end), state.first, target))
            }
        }
        error("no solution found")
    }

    data class Node(val h: Int, val time: Int, val pos: Point)

    override fun solve1(): Int = search(start, end)

    override fun solve2(): Int = search(start, end, search(end, start, search(start, end)))

}