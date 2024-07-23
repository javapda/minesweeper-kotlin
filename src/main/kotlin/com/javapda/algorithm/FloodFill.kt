package com.javapda.algorithm

/**
 * Flood fill
 * https://www.geeksforgeeks.org/flood-fill-algorithm/
 *
 *
 * @constructor Create empty Flood fill
 */
data class ColorCell(val x: Int, val y: Int)
class FloodFill {
    /**
     * Perform flood fill
     *
     * The values in the given 2D screen indicated
     * colors of the pixes. x and y are coordinates
     * of the brush, c is the color that should replace
     * the previous color on screen[x][y] and all
     * surrounding pixels with the same color. Hence
     * all the 2's are replaced with 3's
     *
     * Process
     *   1. create an empty queue (q)
     *   2. push starting location of the pixels as
     *      given in the input and apply the replacement color to it
     *   3. Iterate until q is not empty and pop the front node
     *      (pixel position)
     *   4. check the pixels adjacent to the current pixel and push
     *      them into the queue if valid (had not been colored with
     *      replacement color and have the same color as the old color).
     *
     * @param intArray2D
     * @param x
     * @param y
     * @param newColor
     */
    fun performFloodFill(intArray2D: Array<IntArray>, x: Int, y: Int, prevColor: Int, newColor: Int) {

        println("x=$x, y=$y, c=$newColor")
        val queue = ArrayDeque<ColorCell>()
        queue.add(ColorCell(x, y))

        // color the pixel with the new color
        intArray2D[x][y] = newColor
        val width = intArray2D.maxOf { it.size }
        val height = intArray2D.size
        while (queue.isNotEmpty()) {
            val currPixel = queue.removeFirst()

            val posX = currPixel.x
            val posY = currPixel.y

            // check if adjacent pixels are valid
            // east
            if (isValid(intArray2D, posX + 1, posY, width, height, prevColor, newColor)) {
                // Color with newC
                // if valid and enqueue
                intArray2D[posX + 1][posY] = newColor;
                queue.add(ColorCell(posX + 1, posY))
            }
            // west
            if (isValid(intArray2D, posX - 1, posY, width, height, prevColor, newColor)) {
                // Color with newC
                // if valid and enqueue
                intArray2D[posX - 1][posY] = newColor;
                queue.add(ColorCell(posX - 1, posY))
            }
            // south
            if (isValid(intArray2D, posX, posY + 1, width, height, prevColor, newColor)) {
                // Color with newC
                // if valid and enqueue
                intArray2D[posX][posY + 1] = newColor;
                queue.add(ColorCell(posX, posY + 1))
            }
            // north
            if (isValid(intArray2D, posX, posY - 1, width, height, prevColor, newColor)) {
                // Color with newC
                // if valid and enqueue
                intArray2D[posX][posY - 1] = newColor;
                queue.add(ColorCell(posX, posY - 1))
            }
        }


    }


    /**
     * Is invalid
     *
     * @param width - width of the array (no. of columns)
     * @param height - height of the array (no. of rows)
     * @param prevColor - the starting or existing color to be replaced
     * @param newColor - the new color to replace the prevColor
     * @return
     */
    fun isInvalid(
        intArray2D: Array<IntArray>,
        x: Int,
        y: Int,
        width: Int,
        height: Int,
        prevColor: Int,
        newColor: Int
    ): Boolean {
        return (x < 0 || x > width - 1 || y < 0 || y > height - 1 || intArray2D[x][y] != prevColor || intArray2D[x][y] == newColor)
    }

    fun isValid(
        intArray2D: Array<IntArray>,
        x: Int,
        y: Int,
        width: Int,
        height: Int,
        prevColor: Int,
        newColor: Int
    ): Boolean = !isInvalid(intArray2D, x, y, width, height, prevColor, newColor)

    private fun arrayAsText(intArray2D: Array<IntArray>): String {
        return buildString {
            intArray2D.forEach { nestedArray ->
                appendLine(nestedArray.joinToString(" "))
            }
        }
    }

    fun showArray(intArray2D: Array<IntArray>) {
        println(arrayAsText(intArray2D))
    }
}

fun main() {
    println("Hello Flood Fill")
}