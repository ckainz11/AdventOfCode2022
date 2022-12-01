package util

import java.net.URL

fun getResourceAsText(path: String): String {
    return object {}.javaClass.classLoader.getResource(path)!!.readText()
}

fun getInputAsText(day: Int): String {
    return getResourceAsText("./day${day}.in").trim()
}

fun getInputAsIntArray(day: Int): List<Int> {
    return getInputAsText(day)
        .lines()
        .map { it.toInt() }
}
fun getInputAsIntMatrix(day: Int): Matrix<Int>  {
    return getInputAsText(day)
        .lines()
        .map { it.toCharArray().map {c -> c.toString().toInt()} }

}