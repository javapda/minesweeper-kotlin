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
    fun mineLocationsList() = mineLocations.toList()
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

    fun isNumberedSafeCellLocation(location: MineLocation): Boolean = location in numberedSafeCellLocations()
    fun numberedSafeCellLocations(): List<MineLocation> {
        val numberedSafeCellLocations = mutableListOf<MineLocation>()
        val (width, height) = mineFieldDimensions
        for (row in 0 until height) {
            for (col in 0 until width) {
                val location = MineLocation(col, row)
                if (isSafeCell(location) && isSafeCellNextToAnyMine(location) && !isMineAtLocation(location)) {
                    numberedSafeCellLocations.add(location)
                }
            }
        }
        return numberedSafeCellLocations.toList()
    }

    fun unnumberedSafeCellLocations(): List<MineLocation> {
        val unnumberedSafeCellLocations = mutableListOf<MineLocation>()
        val (width, height) = mineFieldDimensions
        for (row in 0 until height) {
            for (col in 0 until width) {
                val location = MineLocation(col, row)
                if (isSafeCell(location) && !isSafeCellNextToAnyMine(location) && !isMineAtLocation(location)) {
                    unnumberedSafeCellLocations.add(location)
                }
            }
        }
        return unnumberedSafeCellLocations.toList()
    }

    fun prepareForBattleView(
        userMineMarks: Set<MineLocation>,
        userFreeMarks: Set<MineLocation> = setOf<MineLocation>(),
        showMines: Boolean = false,
        showNumbers: Boolean = false
    ): String {
        val (width, height) = mineFieldDimensions
        return buildString {

            appendLine(" |${(1..width).joinToString("")}|")
            appendLine("-|${"-".repeat(width)}|")
            for (row in 0 until height) {
                append(row + 1)
                append("|")
                for (col in 0 until width) {
                    val location = MineLocation(col, row)
                    append(
                        if (location in userMineMarks) {
                            "*"
                        } else if (mineLocations.contains(location)) {
                            if (showMines) {
                                MarkingSymbol.MINE.indicator
                            } else {
                                MarkingSymbol.SAFE_CELL.indicator
                            }
                        } else if (showNumbers && isSafeCellNextToAnyMine(location)) {
                            numberOfMinesNextToLocation(location)
                        } else if (location in userFreeMarks) {
                            if (isSafeCellNextToAnyMine(location)) {
                                numberOfMinesNextToLocation(location)
                            } else {
                                "/"
                            }
                        } else {
                            MarkingSymbol.SAFE_CELL.indicator
                        }
                    )
                }
                appendLine("|")
            }
            appendLine("-|${"-".repeat(width)}|")
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
    private fun isNumberOfMinesPossible(mineCount: Int) = mineCount >= 0 &&
            mineCount < mineFieldDimensions.first * mineFieldDimensions.second

    fun isValidProposedMineCount(mineCount: Int): Boolean {
        return if (!isNumberOfMinesPossible(mineCount)) {
            if (mineCount < 0) {
                println("number of mines must be 0 or more - you entered '$mineCount'")
            } else {
                println(
                    "$mineCount mines cannot fit in mine field " +
                            "of ${mineFieldDimensions.first}x${mineFieldDimensions.second}, " +
                            "max is '${mineFieldDimensions.first * mineFieldDimensions.second}'"
                )
            }
            false
        } else true
    }

    fun setMinesRandomized(mineCount: Int, excludeMinesHere: List<MineLocation?> = listOf<MineLocation>()) {
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
            if (perspectiveLocation !in mineLocations && perspectiveLocation !in excludeMinesHere) {
                mineLocations.add(perspectiveLocation)
                leftToMine--
            }
        }

    }

    /**
     * All mines not marked or excess mines marked by user
     *
     * @param userMarks
     * @return
     */
    fun allMinesNotMarkedOrExcessMinesMarkedByUser(userMarks: Set<MineLocation>): Boolean =
        !(mineLocations.all { loc -> loc in userMarks } && userMarks.all { loc -> loc in mineLocations })

    /**
     * Flood fill
     * given a starting place look N/S/E/W for other free or numbered spaces and add to a
     * collection to be returned
     * An error came up related to a field marked as mine '*' by user, then later found
     * to be NOT a mine during the flooding operation, so check the user set mine locations
     * and remove if there
     *
     * @param location
     * @return list of safe cells marked with '/' or a number of mines nearby
     */
    fun floodFill(location: MineLocation): Set<MineLocation> {
        val locations = mutableSetOf<MineLocation>()
        fun isInvalid(
            x: Int,
            y: Int,
            width: Int,
            height: Int,
        ): Boolean {
            return (x < 0 || x > width - 1 || y < 0 || y > height - 1 || isMineAtLocation(
                MineLocation(
                    x,
                    y
                )
            ) || MineLocation(x, y) in locations)
        }

        fun isValid(
            x: Int,
            y: Int,
            width: Int,
            height: Int,
        ): Boolean = !isInvalid(x, y, width, height)


        val queue = ArrayDeque<MineLocation>()
        queue.add(location)

        val (width, height) = mineFieldDimensions
        while (queue.isNotEmpty()) {
            val currLocation = queue.removeFirst()
            val posX = currLocation.x
            val posY = currLocation.y
            locations.add(currLocation)

            // check if adjacent pixels are valid
            // east
            if (isValid(posX + 1, posY, width, height)) {
                queue.add(MineLocation(posX + 1, posY))
            }
            // west
            if (isValid(posX - 1, posY, width, height)) {
                // Color with newC
                // if valid and enqueue
                queue.add(MineLocation(posX - 1, posY))
            }
            // south
            if (isValid(posX, posY + 1, width, height)) {
                // Color with newC
                // if valid and enqueue
                queue.add(MineLocation(posX, posY + 1))
            }
            // north
            if (isValid(posX, posY - 1, width, height)) {
                // Color with newC
                // if valid and enqueue
                queue.add(MineLocation(posX, posY - 1))
            }

        }

        return locations
    }

}

class Minesweeper(private var mineFieldDimensions: Pair<Int, Int> = 9 to 9, private var numberOfMines: Int = 0) {
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

    fun stage5() {
        mineField = MineField(_mineFieldDimensions = 9 to 9)
        print("How many mines do you want on the field? > ")
        numberOfMines = readln().toInt()
        var firstCellExplored: MineLocation? = null
        while (!mineField.isValidProposedMineCount(numberOfMines)) {
            print("How many mines do you want on the field? > ")
            numberOfMines = readln().toInt()
        }
        print(mineField.prepareForBattleView(setOf()))
        val userMarkedAsMineCells = mutableSetOf<MineLocation>()
        val userMarkedFreeCells = mutableSetOf<MineLocation>()
        fun info() {
            val mineCoordinates =
                mineField.mineLocationsList().mapIndexed { idx, loc -> "(${loc.x + 1},${loc.y + 1})" }
                    .joinToString(", ")
            val numberedCoordinates =
                mineField.numberedSafeCellLocations().mapIndexed { idx, loc -> "(${loc.x + 1},${loc.y + 1})" }
                    .joinToString(", ")
            val unnumberedCoordinates =
                mineField.unnumberedSafeCellLocations().joinToString(", ") { loc -> "(${loc.x + 1},${loc.y + 1})" }
            val userMarkedMineCoordinates =
                userMarkedAsMineCells.joinToString(", ") { loc -> "(${loc.x + 1},${loc.y + 1})" }
            val userMarkedFreeCoordinates =
                userMarkedFreeCells.joinToString(", ") { loc -> "(${loc.x + 1},${loc.y + 1})" }
            val totalSafeCellsNumberedAndUnnumbered =
                (mineField.unnumberedSafeCellLocations() + mineField.numberedSafeCellLocations()).size
            val remainingUnmarkedSafeCells = totalSafeCellsNumberedAndUnnumbered - userMarkedFreeCells.size
            println(
                """
                info
                Minefield
                  dimensions:                                  ${mineField.mineFieldDimensions.first}x${mineField.mineFieldDimensions.second}
                  no. of mines:                                ${mineField.numberOfMines()}
                  mine locations:                              $mineCoordinates
                  no. of numbered locations:                   ${mineField.numberedSafeCellLocations().size}
                  numbered locations:                          $numberedCoordinates
                  no. of unnumbered locations:                 ${mineField.unnumberedSafeCellLocations().size}
                  unnumbered locations:                        $unnumberedCoordinates
                  firstCellExplored:                           ${firstCellExplored ?: "not set yet, do the 'x y free' command to set"}
                  no. user marked mine:                        ${userMarkedAsMineCells.size} 
                  user marked mine locations:                  $userMarkedMineCoordinates
                  no. user marked free:                        ${userMarkedFreeCells.size}
                  user marked free locations:                  $userMarkedFreeCoordinates
                  no. total safe cells numbered + unnumbered:  $totalSafeCellsNumberedAndUnnumbered
                  no. remaining unmarked safe cells:           $remainingUnmarkedSafeCells
                  
            """.trimIndent()
            )
        }

        var userBlownUp = false
        fun gameStillOn(): Boolean {
            val allMinesMarked = mineField.mineLocationsList().isNotEmpty() &&
                    userMarkedAsMineCells != mineField.mineLocationsList().toSet()
            val allFreeMarked =
                (mineField.unnumberedSafeCellLocations() + mineField.numberedSafeCellLocations()) == userMarkedFreeCells

            fun allMinesMarked(): Boolean = firstCellExplored == null || allMinesMarked || allFreeMarked

            return !userBlownUp && allMinesMarked()
        }


        fun doUserInput() {
            print("Set/unset mines marks or claim a cell as free: > ")

            val userInput = readln().lowercase().trim().split(" ")
            if (userInput.size == 1 && userInput.first() in listOf(
                    "?",
                    "help",
                    "userminefield",
                    "info",
                    "reset",
                    "showmines"
                )
            ) {
                val command = userInput.first()
                when (command) {
                    "help" -> {
                        help()
                        return
                    }

                    "?" -> {
                        help()
                        return
                    }

                    "reset" -> {
                        userMarkedAsMineCells.clear()
                        userMarkedFreeCells.clear()
                        mineField.clearMines()
                        mineField.setMinesRandomized(numberOfMines, excludeMinesHere = listOf(firstCellExplored))
                        return
                    }

                    "userminefield" -> {
                        println(mineField.prepareForBattleView(userMarkedAsMineCells, userMarkedFreeCells))
                        return
                    }

                    "showmines" -> {
                        println(
                            "\n" + mineField.prepareForBattleView(
                                setOf(),
                                showMines = true,
                                showNumbers = true
                            )
                        )
                        return
                    }

                    "info" -> {
                        info()
                        return
                    }
                }
            } else if (userInput.size == 3) {
                var (x, y, mark) = userInput.let { Triple(it[0].toInt().dec(), it[1].toInt().dec(), it[2].lowercase()) }
                if (false) {
                    println(
                        """
                x:     $x
                y:     $y
                mark:  $mark
            """.trimIndent()
                    )
                }
                val validMineLocationCommands = listOf("free", "mine")
                fun isValidMineLocationCommand(command: String): Boolean {
                    return if (command in validMineLocationCommands) {
                        true
                    } else {
                        println("'$command' is invalid, must be one of ${validMineLocationCommands.joinToString(", ")}")
                        false
                    }
                }
                if (isValidMineLocationCommand(mark)) {
                    val location = MineLocation(x, y)
                    if (mark == "free") {
                        if (firstCellExplored == null) {
                            firstCellExplored = location
                            mineField.setMinesRandomized(numberOfMines, excludeMinesHere = listOf(firstCellExplored))
                            // flood fill based on starting point
                            val floodFillMarks = mineField.floodFill(location)
                            // remove any user mine marker is in the floowFillMarks
                            floodFillMarks.forEach { loc ->
                                if (loc in userMarkedAsMineCells) {
                                    userMarkedAsMineCells.remove(loc)
                                }

                            }
                            userMarkedFreeCells.addAll(floodFillMarks)
//
//                            userMarkedFreeCells.addAll(mineField.floodFill(location))
                        } else if (userMarkedFreeCells.contains(location)) {
                            userMarkedFreeCells.remove(location)
                        } else {
                            if (mineField.isMineAtLocation(location)) {
//                                println("You stepped on a mine and failed!")
                                userBlownUp = true
                            } else if (mineField.isNumberedSafeCellLocation(location)) {
                                userMarkedFreeCells.add(location)
                            } else {
                                // flood fill based on starting point
                                val floodFillMarks = mineField.floodFill(location)
                                // remove any user mine marker is in the floowFillMarks
                                floodFillMarks.forEach { loc ->
                                    if (loc in userMarkedAsMineCells) {
                                        userMarkedAsMineCells.remove(loc)
                                    }

                                }
                                userMarkedFreeCells.addAll(floodFillMarks)
                            }


                        }

                    } else if (mark == "mine") {
                        if (userMarkedAsMineCells.contains(location)) userMarkedAsMineCells.remove(location)
                        else userMarkedAsMineCells.add(location)
                    } else {
                        throw Exception(
                            "unknown or unhandled mine location command '$mark', expected one of " +
                                    validMineLocationCommands.joinToString(", ")
                        )
                    }
                }
            } else {
                println("input should be 3 arguments of form: <x> <y> <command>")
                return
            }

            print(
                if (gameStillOn())
                    mineField.prepareForBattleView(userMarkedAsMineCells, userMarkedFreeCells)
                else
                    mineField.prepareForBattleView(
                        userMarkedAsMineCells,
                        userMarkedFreeCells,
                        showMines = true,
                        showNumbers = true
                    )
            )
        }
        while (gameStillOn()) {
            doUserInput()
        }
        println(
            if (userBlownUp) {
                "You stepped on a mine and failed!"
            } else {
                "Congratulations! You found all the mines!"
            }
        )
    }

    private fun help() {
        println(
            """
            Minesweeper (c) 2024, all rights reserved :-)
            User entry format:
               x y free - to mark cell as explored
               x y mine - to mark cell as having a mine
            Superuser commands:
               info - show various information about the mine field, etc.
               userminefield - current state of the player's mine field
               reset - resets the mines and markers, but preserves the firstCellExplored
               showmines - displays mine field with mines marked as 'X'
            Legend:
               . - unexplored cells
               / - explored free cell without any mines around it
               # (1-8) as explored free cells with 1 to 8 mines around them
               X - mine
            
        """.trimIndent()
        )
    }

    fun stage4() {
        print("How many mines do you want on the field? > ")
        mineField = MineField(_mineFieldDimensions = 9 to 9)
        numberOfMines = readln().toInt()
        val userMarks = mutableSetOf<MineLocation>()
        mineField.setMinesRandomized(numberOfMines)
        print(mineField.prepareForBattleView(userMarks))
        print("Set/delete mines marks (x and y coordinates): > ")
        val numberedLocations = mineField.numberedSafeCellLocations()
        while (mineField.allMinesNotMarkedOrExcessMinesMarkedByUser(userMarks)) {
            print("Set/delete mines marks (x and y coordinates): > ")
            val (x, y) = readln().split(" ").map(String::toInt).map(Int::dec)
            val location = MineLocation(x, y)
            if (location in numberedLocations) {
                println("There is a number here!")
            } else {
                if (location in userMarks) {
                    userMarks.remove(location)
                } else {
                    userMarks.add(location)
                }
                print(mineField.prepareForBattleView(userMarks))
            }
        }
        println("Congratulations! You found all the mines!")
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
    Minesweeper().stage5()
//    Minesweeper().stage4()
}