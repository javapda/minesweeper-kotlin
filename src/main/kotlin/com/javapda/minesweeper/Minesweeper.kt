package com.javapda.minesweeper

interface Point {
    val x: Int
    val y: Int
}

data class Dimensions(val width: Int, val height: Int)

data class MineLocation(override val x: Int, override val y: Int) : Point {
    constructor(location: Point) : this(location.x, location.y)

    init {
        require(x >= 0)
        require(y >= 0)
    }

    fun nw(): MineLocation = MineLocation(x - 1, y - 1)
    fun n(): MineLocation = MineLocation(x, y - 1)
    fun ne(): MineLocation = MineLocation(x + 1, y - 1)
    fun w(): MineLocation = MineLocation(x - 1, y)
    fun e(): MineLocation = MineLocation(x + 1, y)
    fun sw(): MineLocation = MineLocation(x - 1, y + 1)
    fun s(): MineLocation = MineLocation(x, y + 1)
    fun se(): MineLocation = MineLocation(x + 1, y + 1)

    fun isWithinMineField(mineFieldDimensions: Dimensions): Boolean =
        x < mineFieldDimensions.width && y < mineFieldDimensions.height

    fun isNotWithinMineField(mineFieldDimensions: Dimensions): Boolean =
        !isWithinMineField(mineFieldDimensions)
}

/**
 * Mine field
 * represents the actual mine field data
 * includes the dimensions of the field as well as the locations of each of the mines
 *
 * @property mineFieldDimensions
 * @property numberOfMines
 * @constructor Create empty Mine field
 */
class MineField(_mineFieldDimensions: Pair<Int, Int> = 9 to 9) {
    var mineFieldDimensions: Pair<Int, Int> = _mineFieldDimensions
        set(value) {
            if (value != field) {
                check(isClear()) { "must clear mine [${numberOfMines()}] field before setting dimensions '$value'" }
                field = value
            }
        }

    enum class MarkingSymbol(val indicator: String) {
        MINE("X"), SAFE_CELL(".")
    }

    fun width(): Int = mineFieldDimensions.first
    fun height(): Int = mineFieldDimensions.second
    private val mineLocations = mutableSetOf<MineLocation>()
    fun setMine(location: Point): MineField =
        mineLocations.add(MineLocation(location)).let { this }

    fun isMineAtLocation(mineLocation: MineLocation): Boolean = mineLocation in mineLocations

    fun setMines(locations: List<MineLocation>) = mineLocations.addAll(locations)
    fun clearMine(location: Point): Boolean =
        mineLocations.remove(MineLocation(location))

    fun clearMines(): MineField = mineLocations.clear().let { this }
    fun numberOfMines(): Int = mineLocations.size

    fun mineView(): String {
        val (width, height) = mineFieldDimensions
        return buildString {
            for (row in 0 until height) {
                for (col in 0 until width) {
                    append(
                        if (mineLocations.contains(MineLocation(col, row)))
                            MarkingSymbol.MINE.indicator
                        else MarkingSymbol.SAFE_CELL.indicator
                    )
                }
                appendLine()
            }
        }

    }

    /**
     * Look around view
     * shows Mines and the number of mines on boundary of a safe cell
     *
     * @return
     */
    fun lookAroundView(): String {
        val (width, height) = mineFieldDimensions
        return buildString {
            for (row in 0 until height) {
                for (col in 0 until width) {
                    append(
                        if (mineLocations.contains(MineLocation(col, row)))
                            MarkingSymbol.MINE.indicator
                        else if (isSafeCellNextToAnyMine(MineLocation(col, row))) {
                            numberOfMinesNextToLocation(MineLocation(col, row))
                        } else MarkingSymbol.SAFE_CELL.indicator
                    )
                }
                appendLine()
            }
        }

    }

    private fun numberOfMinesNextToLocation(location: MineLocation): Int = minesNextToLocation(location).size
    fun minesNextToLocation(location: MineLocation): List<MineLocation> {
        val nearbyMineLocations = mutableListOf<MineLocation>()
        with(location) {
            if (isCorner(location)) {
                if (isCornerNW(location)) {
                    if (isMineAtLocation(e())) nearbyMineLocations.add(e())
                    if (isMineAtLocation(se())) nearbyMineLocations.add(se())
                    if (isMineAtLocation(s())) nearbyMineLocations.add(s())
                } else if (isCornerNE(location)) {
                    if (isMineAtLocation(w())) nearbyMineLocations.add(w())
                    if (isMineAtLocation(sw())) nearbyMineLocations.add(sw())
                    if (isMineAtLocation(s())) nearbyMineLocations.add(s())
                } else if (isCornerSW(location)) {
                    if (isMineAtLocation(n())) nearbyMineLocations.add(n())
                    if (isMineAtLocation(ne())) nearbyMineLocations.add(ne())
                    if (isMineAtLocation(e())) nearbyMineLocations.add(e())
                } else if (isCornerSE(location)) {
                    if (isMineAtLocation(n())) nearbyMineLocations.add(n())
                    if (isMineAtLocation(nw())) nearbyMineLocations.add(nw())
                    if (isMineAtLocation(w())) nearbyMineLocations.add(w())
                } else {
                    throw IllegalStateException("unhandled corner case '$location'")
                }
            } else if (isEdge(location)) {
                if (isEdgeN(location)) {
                    if (isMineAtLocation(w())) nearbyMineLocations.add(w())
                    if (isMineAtLocation(sw())) nearbyMineLocations.add(sw())
                    if (isMineAtLocation(s())) nearbyMineLocations.add(s())
                    if (isMineAtLocation(se())) nearbyMineLocations.add(se())
                    if (isMineAtLocation(e())) nearbyMineLocations.add(e())
                } else if (isEdgeW(location)) {
                    if (isMineAtLocation(n())) nearbyMineLocations.add(n())
                    if (isMineAtLocation(ne())) nearbyMineLocations.add(ne())
                    if (isMineAtLocation(e())) nearbyMineLocations.add(e())
                    if (isMineAtLocation(se())) nearbyMineLocations.add(se())
                    if (isMineAtLocation(s())) nearbyMineLocations.add(s())
                } else if (isEdgeE(location)) {
                    if (isMineAtLocation(n())) nearbyMineLocations.add(n())
                    if (isMineAtLocation(nw())) nearbyMineLocations.add(nw())
                    if (isMineAtLocation(w())) nearbyMineLocations.add(w())
                    if (isMineAtLocation(sw())) nearbyMineLocations.add(sw())
                    if (isMineAtLocation(s())) nearbyMineLocations.add(s())
                } else if (isEdgeS(location)) {
                    if (isMineAtLocation(w())) nearbyMineLocations.add(w())
                    if (isMineAtLocation(nw())) nearbyMineLocations.add(nw())
                    if (isMineAtLocation(n())) nearbyMineLocations.add(n())
                    if (isMineAtLocation(ne())) nearbyMineLocations.add(ne())
                    if (isMineAtLocation(e())) nearbyMineLocations.add(e())
                } else {
                    throw IllegalStateException("unhandled edge case '$location'")
                }
            } else {
                if (isMineAtLocation(w())) nearbyMineLocations.add(w())
                if (isMineAtLocation(nw())) nearbyMineLocations.add(nw())
                if (isMineAtLocation(n())) nearbyMineLocations.add(n())
                if (isMineAtLocation(ne())) nearbyMineLocations.add(ne())
                if (isMineAtLocation(e())) nearbyMineLocations.add(e())
                if (isMineAtLocation(se())) nearbyMineLocations.add(se())
                if (isMineAtLocation(s())) nearbyMineLocations.add(s())
                if (isMineAtLocation(sw())) nearbyMineLocations.add(sw())
            }
        }
        return nearbyMineLocations
    }

    fun isSafeCellNextToAnyMine(location: MineLocation): Boolean {
        check(isSafeCell(location)) { "location '$location' contains a MINE, so cannot be a Safe Cell" }
        return minesNextToLocation(location).isNotEmpty()
    }

    private fun isEdgeN(location: MineLocation): Boolean = !isCorner(location) && location.y == 0
    private fun isEdgeW(location: MineLocation): Boolean = !isCorner(location) && location.x == 0
    private fun isEdgeE(location: MineLocation): Boolean = !isCorner(location) && location.x == width() - 1
    private fun isEdgeS(location: MineLocation): Boolean = !isCorner(location) && location.y == height() - 1
    private fun isEdge(location: MineLocation): Boolean = !isCorner(location) &&
            (isEdgeN(location) || isEdgeW(location) || isEdgeE(location) || isEdgeS(location))

    private fun isCornerNW(location: MineLocation): Boolean = location == MineLocation(0, 0)
    private fun isCornerNE(location: MineLocation): Boolean = location == MineLocation(width() - 1, 0)
    private fun isCornerSW(location: MineLocation): Boolean = location == MineLocation(0, height() - 1)
    private fun isCornerSE(location: MineLocation): Boolean = location == MineLocation(width() - 1, height() - 1)
    private fun isCorner(location: MineLocation): Boolean =
        isCornerNW(location) || isCornerNE(location) || isCornerSW(location) || isCornerSE(location)


    fun isValidMineLocation(location: MineLocation): Boolean =
        location.y < mineFieldDimensions.second && location.x < mineFieldDimensions.first

    private fun isSafeCell(location: MineLocation): Boolean = location !in mineLocations

    override fun toString(): String = mineView()

    fun isClear(): Boolean = mineLocations.isEmpty()
    private fun isNumberOfMinesPossible(mineCount: Int) =
        mineCount < mineFieldDimensions.first * mineFieldDimensions.second

    fun setMinesRandomized(mineCount: Int) {
        check(isNumberOfMinesPossible(mineCount)) {
            "$mineCount cannot fit in mine field " +
                    "of ${mineFieldDimensions.first}x${mineFieldDimensions.second} : " +
                    "max is '${mineFieldDimensions.first * mineFieldDimensions.second}'"
        }
        clearMines()
        val (width, height) = mineFieldDimensions
        var leftToMine = mineCount

        while (leftToMine > 0) {
            val randX = (0 until width).random()
            val randY = (0 until height).random()
            val perspectiveLocation = MineLocation(randX, randY)
            if (perspectiveLocation !in mineLocations) {
                mineLocations.add(perspectiveLocation)
                leftToMine--
            }
        }

    }
}

class Minesweeper(private var mineFieldDimensions: Pair<Int, Int> = 9 to 9, private var numberOfMines: Int = 1) {
    lateinit var mineField: MineField

    init {
        mineField = MineField(mineFieldDimensions)
    }

    private val mineLocations = MutableList<MarkingSymbol>(mineFieldDimensions.first * mineFieldDimensions.second) {
        MarkingSymbol.SAFE_CELL
    }


    enum class MarkingSymbol(val indicator: String) {
        MINE("X"), SAFE_CELL(".")
    }

    fun stage3() {
        print("How many mines do you want on the field? > ")
        mineField = MineField(_mineFieldDimensions = 9 to 9)
        numberOfMines = readln().toInt()
        mineField.setMinesRandomized(numberOfMines)
        println(mineField.lookAroundView())
    }

    private fun mineFieldAsText(): String {
        val (width, height) = mineFieldDimensions
        return buildString {
            for (row in 0 until height) {
                for (col in 0 until width) {
                    append(mineLocations[row * width + col].indicator)
                }
                appendLine()
            }
        }

    }

    private fun showMineField() = println(mineFieldAsText())

    /**
     * Populate mines
     * finds random places on map
     */
    private fun populateMines() {
        clearMines()
        val (width, height) = mineFieldDimensions
        var leftToMine = numberOfMines
        while (leftToMine > 0) {
            val location = (0 until mineLocations.size).random()
            if (mineLocations[location] != MarkingSymbol.MINE) {
                mineLocations[location] = MarkingSymbol.MINE
                leftToMine--
            }
        }

    }

    private fun clearMines() {
        val (width, height) = mineFieldDimensions
        for (row in 0 until height) {
            for (col in 0 until width) {
                mineLocations[row * width + col] = MarkingSymbol.SAFE_CELL
            }
        }
    }

}

fun main() {
    Minesweeper(mineFieldDimensions = 2 to 2).stage3()
}