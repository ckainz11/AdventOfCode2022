package days.day11

import days.Day

class Day11(override val input: String) : Day<Long>(input) {

    override fun solve1(): Long = playRounds(20, 3)

    override fun solve2(): Long = playRounds(10_000, 1)

    private fun playRounds(n: Int, divide: Long): Long {
        val monkeys = input.split("\n\n").map { Monkey.of(it.lines()) }
        val common = monkeys.fold(1L) { acc, m -> acc * m.test.divisor }
        repeat(n) {
            monkeys.forEach { monkey ->
                monkey.inspectItems(divide, common).forEach { (next, item) -> monkeys[next].items += item }
            }
        }
        return monkeys.business()
    }

    private fun List<Monkey>.business(): Long = sortedByDescending { it.interactions }
        .let { (first, second) -> first.interactions * second.interactions }

    data class Monkey(val items: MutableList<Long>, val test: Test, val operation: (Long) -> Long) {

        var interactions = 0L

        fun inspectItems(divide: Long, common: Long): List<Pair<Int, Long>> = items.map {
            ((operation(it) / divide) % common).let { new -> (test.getNextMonkeyIndex(new) to new) }
        }.also { interactions += it.size; items.clear() }

        companion object {
            fun of(lines: List<String>): Monkey {
                val items = lines[1].substringAfter(": ").split(", ").map(String::toLong)
                val divisor = lines[3].substringAfterLast(" ").toLong()
                val trueMonkey = lines[4].substringAfterLast(" ").toInt()
                val falseMonkey = lines[5].substringAfterLast(" ").toInt()
                val operation = lines[2].substringAfter("= ").split(" ")
                return Monkey(items.toMutableList(), Test(divisor, trueMonkey, falseMonkey)) { old ->
                    val operationValue = if (operation[2] == "old") old else operation[2].toLong()
                    if (operation[1][0] == '+') old + operationValue
                    else old * operationValue
                }
            }
        }
    }

    data class Test(val divisor: Long, val trueMonkey: Int, val falseMonkey: Int) {
        fun getNextMonkeyIndex(itemVal: Long): Int = if (itemVal % divisor == 0L) trueMonkey else falseMonkey
    }
}