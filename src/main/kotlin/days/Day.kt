package days

abstract class Day(open var input: String) {
    abstract fun solve1(): String
    abstract fun solve2(): String
}