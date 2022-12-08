package days.day7

import days.Day

class Day7(override val input: String) : Day(input) {

    private var directories = emptyList<Directory>()

    private fun parseSizes(lines: List<String>): MutableList<Directory> {
        val directories = mutableListOf(Directory(null))
        var current: Directory = directories.first()
        lines.drop(1).forEach { line ->
            when {
                line == "$ cd .." -> current = current.parent!!
                line.startsWith("$ cd") -> Directory(current).also { current.children += it; directories += it; current = it }
                line[0].isDigit() -> current.filesSize += line.substringBefore(" ").toLong()
            }
        }
        return directories
    }

    override fun solve1(): String {
        directories = parseSizes(input.lines())
        return directories.sumOf { if (it.totalSize < 100_000) it.totalSize else 0 }.toString()
    }

    override fun solve2(): String {
        val missing = 30_000_000 - ( 70_000_000 - directories.first().totalSize)
        return directories.filter { it.totalSize >= missing }.minOf { it.totalSize }.toString()
    }

    data class Directory(val parent: Directory?, var children: List<Directory> = emptyList()) {
        val totalSize: Long get() = filesSize + children.sumOf { it.totalSize }
        var filesSize = 0L
    }

}