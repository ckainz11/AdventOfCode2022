package days.day1

import days.Day

object Day1 : Day {
    override fun solve1(input: String): String = input.lines().map(String::toInt).zipWithNext { a, b -> b - a }.count { x -> x > 0 }.toString()

    override fun solve2(input: String): String = input.lines().map(String::toInt).windowed(3).map { it.sum() }
        .zipWithNext {a, b -> b - a}
        .count { x -> x > 0 }.toString()


}

