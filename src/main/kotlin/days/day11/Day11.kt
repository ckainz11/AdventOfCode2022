package days.day11

import days.Day

class Day11(override val input: String) : Day<Long>(input) {

    override fun solve1(): Long = playRounds(20, 3)

    override fun solve2(): Long = playRounds(10_000, 1)


    private fun playRounds(n: Int, divide: Long): Long {
        val monkeys = input.split("\n\n").map { monkeyFrom(it.lines()) }
        val common = monkeys.fold(1L) { acc, m -> acc * m.test.divisor }
        repeat(n) {
            for (monkey in monkeys) {
                val throws = monkey.inspectItems(divide, common)
                for ((next, item) in throws) {
                    monkeys[next].items += item
                }
            }
        }
        return monkeys.business()
    }

    private fun List<Monkey>.business() = sortedByDescending { it.interactions }.take(2)
        .let { (left, right) -> left.interactions * right.interactions }

    private fun monkeyFrom(lines: List<String>): Monkey {
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

    data class Monkey(val items: MutableList<Long>, val test: Test, val operation: (Long) -> Long) {

        var interactions = 0L

        fun inspectItems(divide: Long, common: Long): List<Pair<Int, Long>> = buildList {
            items.forEach {
                ((operation(it) / divide) % common).also { new -> add(test.getNextMonkeyIndex(new) to new) }
            }
            interactions += items.size
            items.clear()
        }

    }

    data class Test(val divisor: Long, val trueMonkey: Int, val falseMonkey: Int) {
        fun getNextMonkeyIndex(itemVal: Long): Int = if (itemVal % divisor == 0L) trueMonkey else falseMonkey
    }

}