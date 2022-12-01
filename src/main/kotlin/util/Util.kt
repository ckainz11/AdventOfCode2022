package util

import java.util.*

typealias Matrix<T> = List<List<T>>
typealias MutableMatrix<T> = MutableList<MutableList<T>>
fun <T> matrixOf(vararg rows: List<T>): Matrix<T> = List(rows.size) {i -> rows[i]}
fun <T> matrixOf(rows: List<List<T>>): Matrix<T> = List(rows.size) {i -> rows[i]}
fun <T> Matrix<T>.toMutableMatrix(): MutableMatrix<T> = this.map { it.toMutableList() }.toMutableList()
fun <T> Matrix<T>.getColumn(col: Int): List<T> = getCol(this, col)
fun <T, R> Matrix<T>.mapMatrix(transform: (T) -> R): Matrix<R> = this.map { it.map(transform) }
fun <T> Matrix<T>.matrixToString(): String = this.joinToString("\n") { it.joinToString(", ") }
fun <T: Comparable<T>> Matrix<T>.matrixMax(): T = this.mapNotNull { it.maxOrNull() }.maxOrNull()!!
fun <T: Comparable<T>> Matrix<T>.matrixMin(): T = this.mapNotNull { it.minOrNull() }.minOrNull()!!
fun <T> Matrix<T>.getColNum(): Int = this[0].size
fun <T> Matrix<T>.getRowNum(): Int = this.size
fun <T> Matrix<T>.transposed(times: Int = 1): Matrix<T> = transposeMatrix(this, times)
fun <T> emptyMatrixOf(rows: Int, columns: Int, default: T) = MutableList(rows) {MutableList(columns) {default} }
fun <T> Matrix<T>.count(predicate: (T) -> Boolean) = this.sumOf { it.count(predicate) }
fun <T> Matrix<T>.getAdjacent(row: Int, col: Int): List<T> = this.getAdjacentCoordinates(row, col).map { it -> this[it.y][it.x] }
fun <T> Matrix<T>.getAdjacentCoordinates(row: Int, col: Int): List<Point> {
    val adjacent = mutableListOf<Point>()
    if(col != 0) adjacent.add(Point(col - 1, row))
    if(col != this.getColNum()-1) adjacent.add(Point(col + 1, row))
    if(row != 0) adjacent.add(Point(col, row - 1))
    if(row != this.getRowNum()-1) adjacent.add(Point(col, row + 1))
    return adjacent
}
fun <T> Matrix<T>.getAdjacentCoordinates(point: Point): List<Point> = getAdjacentCoordinates(point.y, point.x)
fun <T> Matrix<T>.getSurroundingCoordinates(row: Int, col: Int): List<Point> {
    val adjacent = getAdjacentCoordinates(row, col).toMutableList()
    if(col != 0 && row != 0) adjacent.add(Point(col-1, row-1))
    if(col != 0 && row != this.getRowNum() - 1) adjacent.add(Point(col-1, row + 1))
    if(col != this.getColNum() - 1 && row != 0) adjacent.add(Point(col + 1, row - 1))
    if(col != this.getColNum() - 1 && row != this.getRowNum() - 1) adjacent.add(Point(col + 1, row + 1))
    return adjacent
}
fun <T> Matrix<T>.getSurroundingCoordinates(point: Point): List<Point> = this.getSurroundingCoordinates(point.y, point.x)
data class Point(var x: Int, var y: Int)
data class Point3(val x: Int, val y: Int, val z: Int)
fun  String.hexToBinaryString(): String {
    val num = this.uppercase(Locale.getDefault())
    var binary = ""
    for(hex in num){
        when(hex){
            '0' -> binary += "0000"
            '1' -> binary += "0001"
            '2' -> binary += "0010"
            '3' -> binary += "0011"
            '4' -> binary += "0100"
            '5' -> binary += "0101"
            '6' -> binary += "0110"
            '7' -> binary += "0111"
            '8' -> binary += "1000"
            '9' -> binary += "1001"
            'A' -> binary += "1010"
            'B' -> binary += "1011"
            'C' -> binary += "1100"
            'D' -> binary += "1101"
            'E' -> binary += "1110"
            'F' -> binary += "1111"
        }
    }
    return binary
}
//fun <T> permutations(list: List<T>): List<Pair<T,T>> {
//    val permutations = mutableListOf<Pair<T,T>>()
//    for((i,element) in list.withIndex()){
//        for((j,ele) in list.withIndex()){
//            if(i != j)
//                permutations.add(Pair(element, ele))
//        }
//    }
//    return permutations.toList()
//}

fun <E> permutations(list: List<E>, length: Int? = null): Sequence<List<E>> = sequence {
    val n = list.size
    val r = length ?: list.size

    val indices = list.indices.toMutableList()
    val cycles = (n downTo (n - r)).toMutableList()
    yield(indices.take(r).map { list[it] })

    while (true) {
        var broke = false
        for (i in (r - 1) downTo 0) {
            cycles[i]--
            if (cycles[i] == 0) {
                val end = indices[i]
                for (j in i until indices.size - 1) {
                    indices[j] = indices[j + 1]
                }
                indices[indices.size - 1] = end
                cycles[i] = n - i
            } else {
                val j = cycles[i]
                val tmp = indices[i]
                indices[i] = indices[-j + indices.size]
                indices[-j + indices.size] = tmp
                yield(indices.take(r).map { list[it] })
                broke = true
                break
            }
        }
        if (!broke) {
            break
        }
    }
}
/*----- Helper Functions -----*/

private fun <T> transposeMatrix(matrix: Matrix<T>): Matrix<T> = List(matrix.getColNum()) { i -> matrix.getColumn(i) }
private fun <T> transposeMatrix(matrix: Matrix<T>, times: Int): Matrix<T> {
    var newMatrix = matrix
    repeat(times) {
        newMatrix = transposeMatrix(newMatrix)
    }
    return newMatrix
}
/*----- Old functions -----*/

fun transpose(matrix: List<String>): List<String> {
    val col = matrix[0].length
    val row = matrix.size
    val transpose = MutableList(col) { MutableList(row) {' '} }
    for (i in 0..row - 1) {
        for (j in 0..col - 1) {
            transpose[j][i] = matrix[i][j]
        }
    }
    return transpose.map { it -> it.joinToString("") }
}

fun <T> getCol(array: List<List<T>>, col: Int): List<T> {
    val rows = array.size
    val column = mutableListOf<T>()
    (0 until rows).forEach {
        column.add(array[it][col])
    }
    return column
}