package days

abstract class Day<T>(open val input: String) {
    abstract fun solve1(): T
    abstract fun solve2(): T
}