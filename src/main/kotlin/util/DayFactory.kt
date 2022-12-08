package util

import days.Day
import days.day1.Day1
import days.day2.Day2
import days.day3.Day3
import days.day4.Day4
import days.day5.Day5
import days.day6.Day6
import days.day7.Day7
import days.day8.Day8

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
            8 to ::Day8
        )

        fun getDayObject(day: Int, input: String): Day {
            return days[day]?.invoke(input) ?: Day0("")
        }
    }


}