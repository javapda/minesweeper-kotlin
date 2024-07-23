package com.javapda.algorithm

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class FloodFillTest {
    private lateinit var floodFill: FloodFill
    private lateinit var input2DArray: Array<IntArray>
    private var width: Int = 0
    private var height: Int = 0

    @BeforeEach
    fun setup() {
        floodFill = FloodFill()
        input2DArray = arrayOf(
            intArrayOf(1, 1, 1, 1, 1, 1, 1, 1),
            intArrayOf(1, 1, 1, 1, 1, 1, 0, 0),
            intArrayOf(1, 0, 0, 1, 1, 0, 1, 1),
            intArrayOf(1, 2, 2, 2, 2, 0, 1, 0),
            intArrayOf(1, 1, 1, 2, 2, 0, 1, 0),
            intArrayOf(1, 1, 1, 2, 2, 2, 2, 0),
            intArrayOf(1, 1, 1, 1, 1, 2, 1, 1),
            intArrayOf(1, 1, 1, 1, 1, 2, 2, 1),
        )
        width = input2DArray.maxOf { it.size }
        assertEquals(8, width)
        height = input2DArray.size
        assertEquals(8, height)
    }

    @Test
    fun performFloodFill() {
        floodFill.showArray(input2DArray)
        FloodFill().performFloodFill(intArray2D = input2DArray, x = 4, y = 4, prevColor = 2, newColor = 3)
        floodFill.showArray(input2DArray)
    }

    @ParameterizedTest
    @MethodSource("invalidFloodInfo")
    fun isInvalid(invalidFloodData: IntArray) {
        println(invalidFloodData)
        val (x, y, prevColor, newColor) = invalidFloodData
        assertTrue(floodFill.isInvalid(input2DArray, x, y, width, height, prevColor, newColor))
    }


    companion object {
        @JvmStatic
        fun invalidFloodInfo(): List<IntArray> {
            return listOf(
                // x, y, prevColor, newColor
                intArrayOf(-1, 0, 1, 3),  // low x
                intArrayOf(1, -1, 1, 3),  // low y
                intArrayOf(8, 0, 1, 3),   // high x
                intArrayOf(0, 23, 1, 3),  // high y
                intArrayOf(0, 0, 2, 3),   // expected color different from actual
                intArrayOf(0, 0, 1, 1),  // same color
            )
        }
    }
}