package days.day22

import days.Day
import util.*

class Day22(override val input: String) : Day<Int>(input) {


    val split = input.split("\n\n")
        .let { it[0].lines().map { l -> l.toList() } to it[1].replace("R", " R ").replace("L", " L ").split(" ") }

    override fun solve1(): Int {
        val (map, path) = split
        var current = map.matrixIndexOfFirst { it == '.' }
        var facing = 0
        instructions@ for (inst in path) {
            if (inst.isTurnInstruction())
                facing = turn(facing, inst)
            else {
                for (i in 1..inst.toInt()) {
                    var next = current + Point.directions[facing]
                    if (facing == 0 && next.x >= map[current.y].size) {
                        next = Point(map[current.y].indexOfFirst { it.isValidTile() }, next.y) //RIGHT
                    } else if (facing == 2 && next.x < map[current.y].indexOfFirst { it.isValidTile() }) {
                        next = Point(map[current.y].indexOfLast { it.isValidTile() }, next.y)
                    }
                    if (facing == 1 || facing == 3) {
                        val column = map.getColumn(current.x)
                        if (next.y >= column.size)
                            next = Point(next.x, column.indexOfFirst { it.isValidTile() }) //DOWN
                        if (next.y < column.indexOfFirst { it.isValidTile() }) {
                            next = Point(next.x, column.indexOfLast { it.isValidTile() }) //UP
                        }
                    }


                    if (map[next] == '#')
                        continue@instructions
                    else current = next
                }
            }

        }
        return (current.y + 1) * 1000 + (current.x + 1) * 4 + facing
    }


    private fun sideModifiers(side: Int): Point = when (side) {
        0 -> Point(50, 0)
        1 -> Point(100, 0)
        2 -> Point(50, 50)
        3 -> Point(0, 100)
        4 -> Point(50, 100)
        5 -> Point(0, 150)
        else -> error("send help")
    }

    override fun solve2(): Int {
        val (map, path) = split
        if (map.size == 12) //example case, I cba mapping out the example cube
            return -1
        val sides = collectSides(map)
        var side = 0
        var facing = Facing.RIGHT
        var current = Point(0, 0)

        instructions@ for (inst in path) {
            if (inst.isTurnInstruction())
                facing = turn(facing, inst)
            else {
                for (i in 1..inst.toInt()) {
                    var next = current + Point.directions[facing.ordinal]
                    if (next.x !in 0..49 || next.y !in 0..49) {
                        val (newSide, newFacing, nextPoint) = cubeWrap(current, facing, side)
                        if (sides[newSide][nextPoint] == '#')
                            continue@instructions
                        else {
                            side = newSide
                            facing = newFacing
                            next = nextPoint
                        }
                    }
                    if (sides[side][next] == '#')
                        continue@instructions
                    else current = next

                }
            }
        }
        return (current + sideModifiers(side)).let { (it.y + 1) * 1000 + (it.x + 1) * 4 + facing.ordinal }
    }

    enum class Facing { RIGHT, DOWN, LEFT, UP }

    private fun Char.isValidTile(): Boolean = this == '.' || this == '#'
    private fun Int.toFacing(): Facing = Facing.values()[this]
    private fun String.isTurnInstruction(): Boolean = this == "R" || this == "L"
    private fun turn(facing: Int, turn: String): Int = (if (turn == "R") facing + 1 else facing - 1).mod(4)
    private fun turn(facing: Facing, turn: String): Facing =
        ((facing.ordinal + if (turn == "R") 1 else -1).mod(4)).toFacing()


    private fun collectSides(map: Matrix<Char>, sideLength: Int = 50): List<Matrix<Char>> {
        val sides = mutableListOf<Matrix<Char>>()
        val xSteps = map.maxOf { it.size } / sideLength
        val ySteps = map.size / sideLength
        for (y in 0 until ySteps) {
            for (x in 0 until xSteps) {
                try {
                    val p = Point(x * sideLength, y * sideLength)
                    if (map[p].isValidTile())
                        sides.add(map.subMatrix(sideLength, sideLength, p))
                } catch (_: IndexOutOfBoundsException) {
                }
            }
        }

        return sides
    }

    private fun cubeWrap(p: Point, f: Facing, side: Int): Triple<Int, Facing, Point> {
        when (side) {
            0 -> {
                if (f == Facing.RIGHT)
                    return Triple(1, Facing.RIGHT, Point(0, p.y))
                if (f == Facing.DOWN)
                    return Triple(2, Facing.DOWN, Point(p.x, 0))
                if (f == Facing.LEFT)
                    return Triple(3, Facing.RIGHT, Point(0, 49 - p.y))
                if (f == Facing.UP)
                    return Triple(5, Facing.RIGHT, Point(0, p.x))
            }

            1 -> {
                if (f == Facing.RIGHT)
                    return Triple(4, Facing.LEFT, Point(49, 49 - p.y))
                if (f == Facing.DOWN)
                    return Triple(2, Facing.LEFT, Point(49, p.x))
                if (f == Facing.LEFT)
                    return Triple(0, Facing.LEFT, Point(49, p.y))
                if (f == Facing.UP)
                    return Triple(5, Facing.UP, Point(p.x, 49))
            }

            2 -> {
                if (f == Facing.RIGHT)
                    return Triple(1, Facing.UP, Point(p.y, 49))
                if (f == Facing.DOWN)
                    return Triple(4, Facing.DOWN, Point(p.x, 0))
                if (f == Facing.LEFT)
                    return Triple(3, Facing.DOWN, Point(p.y, 0))
                if (f == Facing.UP)
                    return Triple(0, Facing.UP, Point(p.x, 49))
            }

            3 -> {
                if (f == Facing.RIGHT)
                    return Triple(4, Facing.RIGHT, Point(0, p.y))
                if (f == Facing.DOWN)
                    return Triple(5, Facing.DOWN, Point(p.x, 0))
                if (f == Facing.LEFT)
                    return Triple(0, Facing.RIGHT, Point(0, 49 - p.y))
                if (f == Facing.UP)
                    return Triple(2, Facing.RIGHT, Point(0, p.x))
            }

            4 -> {
                if (f == Facing.RIGHT)
                    return Triple(1, Facing.LEFT, Point(49, 49 - p.y))
                if (f == Facing.DOWN)
                    return Triple(5, Facing.LEFT, Point(49, p.x))
                if (f == Facing.LEFT)
                    return Triple(3, Facing.LEFT, Point(49, p.y))
                if (f == Facing.UP)
                    return Triple(2, Facing.UP, Point(p.x, 49))
            }

            5 -> {
                if (f == Facing.RIGHT)
                    return Triple(4, Facing.UP, Point(p.y, 49))
                if (f == Facing.DOWN)
                    return Triple(1, Facing.DOWN, Point(p.x, 0))
                if (f == Facing.LEFT)
                    return Triple(0, Facing.DOWN, Point(p.y, 0))
                if (f == Facing.UP)
                    return Triple(3, Facing.UP, Point(p.x, 49))
            }
        }
        error("something wrong help")
    }
}