package days

abstract class Day<T: Any>(open val input: String) {
    abstract fun solve1(): T
    abstract fun solve2(): T
}
