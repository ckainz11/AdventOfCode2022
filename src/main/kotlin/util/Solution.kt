package util

import days.day1.Day1
import days.day2.Day2
import days.day3.Day3
import days.day4.Day4
import util.ConsoleUtils.header
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.system.measureTimeMillis
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.result.Result
import days.day5.Day5
import days.day6.Day6
import days.day7.Day7
import days.day8.Day8

val days = mapOf(
    1 to Day1(""),
    2 to Day2(""),
    3 to Day3(""),
    4 to Day4(""),
    5 to Day5(""),
    6 to Day6(""),
    7 to Day7(""),
    8 to Day8("")
)

class Solution {
    fun greet(): Int {
        println(header)
        println("Merry Christmas! I want to see the solution for day: ")
        return getDay()
    }

    fun solve(day: Int) {
        printDayHeader(day)

        val d = days[day] ?: Day0("")

        val exampleInput = getInputFile(day, true)
        val exampleOutputPart1 = getExampleOut(day, 1)
        val exampleOutputPart2 = getExampleOut(day, 2)
        val realInput = getInputFile(day, false)

        d.input = exampleInput

        //Example Part 1
        var example1ResultState: ResultState
        var actualExampleOutPart1: String
        var example1time = 0L

        try {
            example1time = measureTimeMillis { actualExampleOutPart1 = d.solve1() }
            example1ResultState = if(actualExampleOutPart1 == exampleOutputPart1) ResultState.CORRECT else ResultState.WRONG
        } catch(e: NotImplementedError) {
            example1ResultState = ResultState.WARNING
            actualExampleOutPart1 = "NOT IMPLEMENTED"
        }


        //Example Part 2
        var example2ResultState: ResultState
        var actualExampleOutPart2: String
        var example2time = 0L

        try {
            example2time = measureTimeMillis { actualExampleOutPart2 = d.solve2() }
            example2ResultState = if (actualExampleOutPart2 == exampleOutputPart2) ResultState.CORRECT else ResultState.WRONG
        } catch(e: NotImplementedError) {
            example2ResultState = ResultState.WARNING
            actualExampleOutPart2 = "NOT IMPLEMENTED"
        }


        d.input = realInput

        //Real Part 1
        var actualOutPart1: String
        var actual1time = 0L
        if(example1ResultState != ResultState.WARNING) {
            actual1time = measureTimeMillis { actualOutPart1 = d.solve1() }
        } else {
            actualOutPart1 = "NOT IMPLEMENTED"
        }

        //Real Part 2
        var actualOutPart2: String
        var actual2time = 0L
        if(example2ResultState != ResultState.WARNING) {
             actual2time = measureTimeMillis { actualOutPart2 = d.solve2() }
        }  else {
            actualOutPart2 = "NOT IMPLEMENTED"
        }


        printSolution(
            "$actualExampleOutPart1 (in $example1time ms)",
            exampleOutputPart1,
            "$actualOutPart1 (in $actual1time ms)",
            1,
            example1ResultState
        )




        printSolution(
            "$actualExampleOutPart2 (in $example2time ms)",
            exampleOutputPart2,
            "$actualOutPart2 (in $actual2time ms)",
            2,
            example2ResultState
        )

    }

    private fun printSolution(exampleOut: String, expectedOut: String, out: String, part: Int, resultState: ResultState) {
        printPartHeader(resultState, part)
        println("Example output: $exampleOut")
        println("Expected output: $expectedOut")
        println("Real output: $out")
    }

    private fun printPartHeader(resultState: ResultState, part: Int) {
        val icon = when (resultState) {
            ResultState.CORRECT -> "✅"
            ResultState.WRONG -> "❌"
            ResultState.WARNING -> "⚠️"
        }
        val partHeader = "${ConsoleUtils.BLACK_BACKGROUND_BRIGHT}Part $part $icon"
        print(partHeader)
        println()
        print(ConsoleUtils.RESET)
    }

    private fun getExampleOut(day: Int, part: Int): String {
        if (part != 1 && part != 2) error("Not a valid part")
        val file = File("src/main/resources/day$day" + "_part$part.out")

        return if (file.exists()) file.readText()
        else {
            file.createNewFile()
            return "not found, file has been created"
        }
    }

    private fun getInputFile(day: Int, example: Boolean): String {
        var filePath = "src/main/resources/day$day."
        filePath += if (example) "example" else "in"
        val file = File(filePath)
        return if (file.exists()) file.readText()
        else if (!example) fetchFile(day, file).readText()
        else {
            file.createNewFile()
            return "not found, file has been created"
        }
    }

    private fun fetchFile(day: Int, input: File): File {
        val url = "https://adventofcode.com/2022/day/$day/input"
        val (_, _, result) = Fuel.get(url)
            .header("Cookie", "session=${Config.session}")
            .responseString()
        if (result is Result.Failure) {
            throw result.error
        }

        val body = result.get()
        input.createNewFile()
        input.writeText(body.trim())
        return input
    }


    private fun getDay(): Int {
        val dayIn = readLine() ?: "0"
        var day = try {
            dayIn.toInt()
        } catch (e: NumberFormatException) {
            0
        }
        if (day <= 0 || day > 25) {
            println("Hey! That's not a day of the advent! Doing today's solution instead")
            val sdf = SimpleDateFormat("dd/M")
            val today = sdf.format(Date()).split("/")
            val dayC = today[0].toInt()
            day = dayC
            if (dayC > 25 || today[1].toInt() != 12) {
                println("Advent is over! :( Falling back to Day 1")
                day = 1
            }

        }
        return day
    }


    private fun printDayHeader(day: Int) {
        val sb = StringBuilder()

        val inset = "Day $day"
        val header = "#".repeat(22 + inset.length)
        sb.appendLine(header.makeBlink())
        sb.append("#".makeBlink())
        sb.append("${" ".repeat(10)}$inset${" ".repeat(10)}")
        sb.appendLine("#".makeBlink())
        sb.appendLine(header.makeBlink())

        println(sb.toString())
    }

    fun String.makeBlink(): String {
        val sb = StringBuilder()
        this.toList().forEach {
            sb.append(ConsoleUtils.blinkColors.random() + it + ConsoleUtils.RESET)
        }
        return sb.toString()
    }
    enum class ResultState {
        CORRECT,
        WRONG,
        WARNING
    }
}