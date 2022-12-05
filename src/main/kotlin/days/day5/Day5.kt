package days.day5

import days.Day

object Day5 : Day {

    private fun parseInput(input: String): Pair<Crane, List<Instruction>> {
        val (stacks, inst) = input.split("\n\n").map { it.lines() }
        val stackCount = stacks.takeLast(1)[0].trim().split("\\s+".toRegex()).map { it.toInt() }
        val parsed = emptyList<ArrayDeque<Char>>().toMutableList()
        stackCount.forEach { stackNumber ->
            val stack = ArrayDeque<Char>()
            stacks.dropLast(1).map {it.toList()}.forEach { line ->
                val char = line.getOrNull((stackNumber - 1) * 4 + 1)
                if (char != null && char != ' ')
                    stack.addFirst(char)
            }
            parsed.add(stack)
        }
        return Pair(
            Crane(parsed.toTypedArray()),
            inst.map { ins -> ins.split(" ").let { Instruction(it[1].toInt(), it[3].toInt(), it[5].toInt()) } })
    }

    override fun solve1(input: String): String {
        val (crane, instructions) = parseInput(input)
        return crane.moveAll(instructions, 1)
    }

    override fun solve2(input: String): String {
        val (crane, instructions) = parseInput(input)
        return crane.moveAll(instructions, 2)
    }
}