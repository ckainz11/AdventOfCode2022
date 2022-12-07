package days.day7

import days.Day

object Day7 : Day {
    private fun parseSizes(lines: List<String>): MutableList<Dir> {
        val root = Dir("/")
        val dirs = mutableListOf(root)
        var cur = root

        lines.drop(1).forEach {
            when {
                it.startsWith("$ cd") -> {
                    val name = it.drop(5)
                    cur = if (name == "..") cur.parent!! else cur.children.getValue(name)
                }

                it.startsWith("dir") -> {
                    val name = it.drop(4)
                    val child = Dir(
                        parent = cur,
                        name = name
                    )
                    dirs.add(child)
                    cur.children[name] = child
                }

                it.first().isDigit() -> {
                    val toAdd = it.split(" ").first().toInt()
                    cur.size += toAdd
                    var p = cur.parent
                    while (p != null) {
                        p.size += toAdd
                        p = p.parent
                    }
                }
            }
        }
        return dirs
    }

    override fun solve1(input: String): String {
        return parseSizes(input.lines()).sumOf { if (it.size < 100000) it.size else 0 }.toString()
    }

    override fun solve2(input: String): String {
        val dirs = parseSizes(input.lines())
        val missing = 70000000 - dirs.first().size
        return dirs.filter { it.size >= 30000000 - missing }.minOf { it.size }.toString()
    }

    class Dir(
        val name: String,
        val parent: Dir? = null,
        val children: MutableMap<String, Dir> = mutableMapOf(),
        var size: Int = 0
    )
}