package com.javapda.minesweeper

class Minesweeper() {
    fun stage1() {
        println(
            """
.X.......
.....X..X
....X....
......X..
..X......
....X....
..X......
..X......
......X..            
        """.trimIndent()
        )
    }
}

fun main() {
    Minesweeper().stage1()
}