package util

import days.Day
import days.day1.Day1
import days.day10.Day10
import days.day11.Day11
import days.day12.Day12
import days.day13.Day13
import days.day14.Day14
import days.day15.Day15
import days.day16.Day16
import days.day17.Day17
import days.day18.Day18
import days.day19.Day19
import days.day2.Day2
import days.day20.Day20
import days.day21.Day21
import days.day22.Day22
import days.day23.Day23
import days.day3.Day3
import days.day4.Day4
import days.day5.Day5
import days.day6.Day6
import days.day7.Day7
import days.day8.Day8
import days.day9.Day9

class DayFactory {
    companion object {
        val days = mapOf(
            1 to ::Day1,
            2 to ::Day2,
            3 to ::Day3,
            4 to ::Day4,
            5 to ::Day5,
            6 to ::Day6,
            7 to ::Day7,
            8 to ::Day8,
            9 to ::Day9,
            10 to ::Day10,
            11 to ::Day11,
            12 to ::Day12,
            13 to ::Day13,
            14 to ::Day14,
            15 to ::Day15,
            16 to ::Day16,
            17 to ::Day17,
            18 to ::Day18,
            19 to ::Day19,
            20 to ::Day20,
            21 to ::Day21,
            22 to ::Day22,
            23 to ::Day23
        )

        fun getDayObject(day: Int, input: String): Day<out Any> {
            return days[day]?.invoke(input) ?: error("this day doesnt have a solution yet")
        }
    }


}