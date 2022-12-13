package days.day13

import days.Day

class Day13(override val input: String) : Day<Int>(input) {

    private val packets = input.lines().mapNotNull { if (it.isEmpty()) null else Packet.parse(it) }

    override fun solve1(): Int =
        packets.chunked(2) { (a, b) -> a <= b }.withIndex().filter { it.value }.sumOf { it.index + 1 }

    override fun solve2(): Int = packets.fold(Pair(1, 2)) { acc, i -> //b is bigger than a therefore 1,2 and not 1,1
        Pair(acc.first + if (i < Packet.a) 1 else 0, acc.second + if (i < Packet.b) 1 else 0)
    }.let { (a, b) -> a * b }


    sealed interface Packet : Comparable<Packet> {

        data class NumberPacket(val value: Int) : Packet {
            override fun compareTo(other: Packet): Int = when (other) {
                is NumberPacket -> this.value compareTo other.value
                is ListPacket -> this.toListPacket() compareTo other
            }

        }

        data class ListPacket(val value: List<Packet>) : Packet {
            override infix fun compareTo(other: Packet): Int {
                return when (other) {
                    is NumberPacket -> this compareTo other.toListPacket()
                    is ListPacket -> this.value.zip(other.value) { a, b -> a compareTo b }.sum()
                        .let { if (it != 0) it else this.value.size - other.value.size }
                }
            }
        }

        fun NumberPacket.toListPacket() = ListPacket(listOf(this))

        companion object {
            val a: Packet = ListPacket(listOf(ListPacket(listOf(NumberPacket(2)))))
            val b: Packet = ListPacket(listOf(ListPacket(listOf(NumberPacket(6)))))

            fun parse(line: String): Packet? {
                if (line.isEmpty()) {
                    return null
                }
                if (line[0].isDigit()) {
                    return NumberPacket(line.toInt())
                }

                var bracketCount = 0
                var lastComma = 0

                val packets = mutableListOf<Packet?>()

                line.forEachIndexed { i, c ->
                    when (c) {
                        '[' -> bracketCount++
                        ']' -> {
                            bracketCount--
                            if (bracketCount == 0) {
                                packets += parse(line.take(i).drop(lastComma + 1))
                            }
                        }

                        ',' -> {
                            if (bracketCount == 1) {
                                packets += parse(line.take(i).drop(lastComma + 1))
                                lastComma = i
                            }
                        }
                    }
                }

                return ListPacket(packets.filterNotNull())
            }
        }
    }
}