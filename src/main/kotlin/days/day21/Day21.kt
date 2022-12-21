package days.day21

import days.Day
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class Day21(override val input: String) : Day<Long>(input) {

    private val monkeys = input.lines().map { Monkey.from(it) }.associateBy { it.id }.toMutableMap()


    override fun solve1(): Long = monkeys[Monkey.ROOT]?.yell(monkeys)!!.toLong()

    override fun solve2(): Long {
        var max = Long.MAX_VALUE
        var min = 0L
        var answer: Pair<Long, Double>? = null

        monkeys[Monkey.ROOT] = (monkeys[Monkey.ROOT] as Monkey.Operation).let {
            Monkey.Operation(it.id, it.left, it.right) { a, b -> abs(a - b) }
        }
        while (answer == null) {
            answer = listOf(min, (min + max) / 2, max).map {
                monkeys[Monkey.HUMAN] = Monkey.Constant(Monkey.HUMAN, it.toDouble())
                it to monkeys[Monkey.ROOT]?.yell(monkeys)!!
            }.sortedBy { it.second }.let { res ->
                res.take(2).map(Pair<Long, Double>::first).also { (best, best2) ->
                    min = min(best, best2)
                    max = max(best, best2)
                }
                res[0].let { if(it.second == 0.0) it else null}
            }
        }

        return answer.first
    }

    sealed class Monkey(val id: String) {

        abstract fun yell(monkeys: Map<String, Monkey>): Double

        class Constant(id: String, private val num: Double) : Monkey(id) {
            override fun yell(monkeys: Map<String, Monkey>): Double = num
        }

        class Operation(id: String, val left: String, val right: String, private val op: (Double, Double) -> Double) :
            Monkey(id) {
            override fun yell(monkeys: Map<String, Monkey>): Double =
                op(monkeys[left]!!.yell(monkeys), monkeys[right]!!.yell(monkeys))
        }

        companion object {

            const val HUMAN = "humn"
            const val ROOT = "root"

            fun from(line: String): Monkey =
                line.split(": ").let { (id, calc) ->
                    calc.split(" ").let {
                        if (it.size == 1)
                            Constant(id, it[0].toDouble())
                        else {
                            val op: (Double, Double) -> Double = when (it[1]) {
                                "+" -> Double::plus
                                "*" -> Double::times
                                "-" -> Double::minus
                                "/" -> Double::div
                                else -> throw IllegalArgumentException("${it[1][0]} is not a valid operation")
                            }
                            Operation(id, it[0], it[2], op)
                        }
                    }
                }

        }
    }

}