package days.day7

import days.Day

class Day7(override val input: String) : Day<Long>(input) {

    private var directories = buildList {
        var current: Directory = Directory(null) .also { add(it) }
        input.lines().drop(1).forEach { line ->
            when {
                line == "$ cd .." -> current = current.parent!!
                line.startsWith("$ cd") -> Directory(current).also { current.children += it; add(it); current = it }
                line[0].isDigit() -> current.filesSize += line.substringBefore(" ").toLong()
            }
        }
    }

    override fun solve1(): Long = directories.sumOf { if (it.totalSize < 100_000) it.totalSize else 0 }


    override fun solve2(): Long = (30_000_000 - ( 70_000_000 - directories.first().totalSize))
        .let { missing -> directories.filter { it.totalSize >= missing }.minOf { it.totalSize } }


    data class Directory(val parent: Directory?, var children: List<Directory> = emptyList()) {
        val totalSize: Long get() = filesSize + children.sumOf { it.totalSize }
        var filesSize = 0L
    }

}