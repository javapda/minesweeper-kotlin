package com.javapda.minesweeper

class Minesweeper() {
    var mineFieldDimensions = 9 to 9
    val mineLocationsOn9x9Field = MutableList<MarkingSymbol>(mineFieldDimensions.first * mineFieldDimensions.second) {
        MarkingSymbol.SAFE_CELL
    }

    var numberOfMines = 0

    enum class MarkingSymbol(val indicator: String) {
        MINE("X"), SAFE_CELL(".")
    }

    fun stage2() {
        print("How many mines do you want on the field? > ")
        numberOfMines = readln().toInt()
        if (numberOfMines > mineLocationsOn9x9Field.size) {
            throw IllegalArgumentException(
                "${mineFieldDimensions.first}x${mineFieldDimensions.second} mine field " +
                        "can have at most '${mineFieldDimensions.first * mineFieldDimensions.second}' mines...you asked " +
                        "for '${numberOfMines}'"
            )
        } else if (numberOfMines < 0) {
            numberOfMines = (0 until (mineLocationsOn9x9Field.size / 4)).random()
            println("cannot have less than 0 mines, setting number of mines to '$numberOfMines'")
        }
        populateMines()
        showMineField()
    }

    private fun showMineField() {
        val (width, height) = mineFieldDimensions
        for (row in 0 until height) {
            for (col in 0 until width) {
                print(mineLocationsOn9x9Field[row * width + col].indicator)
            }
            println()
        }
    }

    /**
     * Populate mines
     * finds random places on map
     */
    private fun populateMines() {
        clearMines()
        val (width, height) = mineFieldDimensions
        var leftToMine = numberOfMines
        while (leftToMine > 0) {
            val location = (0 until mineLocationsOn9x9Field.size).random()
            if (mineLocationsOn9x9Field[location] != MarkingSymbol.MINE) {
                mineLocationsOn9x9Field[location] = MarkingSymbol.MINE
                leftToMine--
            }
        }

    }

    private fun clearMines() {
        val (width, height) = mineFieldDimensions
        for (row in 0 until height) {
            for (col in 0 until width) {
                mineLocationsOn9x9Field[row * width + col] = MarkingSymbol.SAFE_CELL
            }
        }
    }

}

fun main() {
    Minesweeper().stage2()
}