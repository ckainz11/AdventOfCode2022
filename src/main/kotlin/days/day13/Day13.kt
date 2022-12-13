package days.day13

import days.Day

class Day13(override val input: String) : Day<Int>(input) {

    private val packets = input.lines().mapNotNull { if (it.isEmpty()) null else PacketValue.parse(it) }

    override fun solve1(): Int =
        packets.chunked(2) { (a, b) -> a <= b }.withIndex().filter { it.value }.sumOf { it.index + 1 }

    private val a: PacketValue = PacketValue.ListValue(listOf(PacketValue.ListValue(listOf(PacketValue.NumberValue(2)))))
    private val b: PacketValue = PacketValue.ListValue(listOf(PacketValue.ListValue(listOf(PacketValue.NumberValue(6)))))

    override fun solve2(): Int = packets.fold(Pair(1, 2)) { acc, i -> //b is bigger than a therefore 1,2 and not 11
        Pair(acc.first + if (i < a) 1 else 0, acc.second + if (i < b) 1 else 0)
    }.let { (a, b) -> a * b }


    sealed interface PacketValue : Comparable<PacketValue> {

        data class NumberValue(val value: Int) : PacketValue {
            override fun compareTo(other: PacketValue): Int = when (other) {
                is NumberValue -> this.value compareTo other.value
                is ListValue -> this.toListValue() compareTo other
            }

        }

        data class ListValue(val value: List<PacketValue>) : PacketValue {
            override infix fun compareTo(other: PacketValue): Int {
                return when (other) {
                    is NumberValue -> this compareTo other.toListValue()
                    is ListValue -> {
                        val i = this.value.iterator()
                        val j = other.value.iterator()
                        while (i.hasNext() && j.hasNext()) {
                            val comparison = i.next() compareTo j.next()
                            if (comparison != 0) return comparison
                        }
                        i.hasNext() compareTo j.hasNext()
                    }
                }
            }
        }

        fun NumberValue.toListValue() = ListValue(listOf(this))

        companion object {


            fun parse(line: String): PacketValue? {
                if (line.isEmpty()) {
                    return null
                }
                if (line[0].isDigit()) {
                    return NumberValue(line.toInt())
                }

                var bracketCount = 0
                var lastComma = 0

                val packets = mutableListOf<PacketValue?>()

                for ((i, c) in line.withIndex()) {
                    if (c == '[') {
                        bracketCount++
                    } else if (c == ']') {
                        bracketCount--
                        if (bracketCount == 0) {
                            packets += parse(line.take(i).drop(lastComma + 1))
                        }
                    }
                    if (c == ',') {
                        if (bracketCount == 1) {
                            packets += parse(line.take(i).drop(lastComma + 1))
                            lastComma = i
                        }
                    }
                }

                return ListValue(packets.filterNotNull())
            }
        }
    }
}