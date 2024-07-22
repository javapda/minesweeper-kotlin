package com.javapda.minesweeper

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class MineFieldTest {
    lateinit var mineField: MineField

    @BeforeEach
    fun setup() {
        mineField = MineField()
        assertTrue(mineField.isClear())
    }

    @Test
    fun minesNextToLocationCenter() {
        mineField.mineFieldDimensions = 3 to 3
        mineField.setMine(MineLocation(1, 1))
        assertEquals(1, mineField.minesNextToLocation(MineLocation(0, 0)).size)
        assertEquals(1, mineField.minesNextToLocation(MineLocation(1, 0)).size)
        assertEquals(1, mineField.minesNextToLocation(MineLocation(2, 0)).size)
        assertEquals(1, mineField.minesNextToLocation(MineLocation(0, 1)).size)
        assertEquals(1, mineField.minesNextToLocation(MineLocation(2, 1)).size)
        assertEquals(1, mineField.minesNextToLocation(MineLocation(0, 2)).size)
        assertEquals(1, mineField.minesNextToLocation(MineLocation(1, 2)).size)
        assertEquals(1, mineField.minesNextToLocation(MineLocation(2, 2)).size)
    }

    @Test
    fun isValidMineLocation() {
        mineField.mineFieldDimensions = 3 to 3
        with(mineField) {
            assertTrue(isValidMineLocation(MineLocation(0, 0)))
            assertTrue(isValidMineLocation(MineLocation(1, 1)))
            assertTrue(isValidMineLocation(MineLocation(2, 2)))
            assertFalse(isValidMineLocation(MineLocation(2, 3)))
            assertFalse(isValidMineLocation(MineLocation(3, 2)))
            assertFalse(isValidMineLocation(MineLocation(3, 5)))
        }

    }

    @ParameterizedTest
    @MethodSource("mineLocations")
    fun testIsMineAtLocation(mineLocation: MineLocation) {
        assertTrue(
            mineField
                .clearMines()
                .setMine(mineLocation)
                .isMineAtLocation(mineLocation)
        )
    }


    @Test
    fun mineView() {
        mineField.clearMines()
        assertTrue(mineField.isClear())
        mineField.mineFieldDimensions = 9 to 9
        mineField.setMines(listOf(MineLocation(0, 0), MineLocation(1, 1)))
        assertFalse(mineField.isClear())
        println(mineField.mineView())
    }

    @Test
    fun randomizedMines() {
        mineField.mineFieldDimensions = 5 to 5
        mineField.setMinesRandomized(10)
        println(mineField.mineView())
    }

    @Test
    fun lookAroundView() {
        mineField.mineFieldDimensions = 5 to 5
        mineField.setMine(MineLocation(0, 0))
        mineField.setMine(MineLocation(2, 2))
        println(mineField.lookAroundView())
    }

    @Test
    fun setMine() {
        val mf = MineField(_mineFieldDimensions = 2 to 2)
        assertEquals(2, mf.width())
        assertEquals(2, mf.height())
        mf.clearMines()
        assertEquals(0, mf.numberOfMines())
        mf.setMine(MineLocation(0, 0))
        mf.setMine(MineLocation(1, 1))
        assertEquals(2, mf.numberOfMines())
        println("MINE NEXT")
        println(mf.toString())

    }

    companion object {
        @JvmStatic
        fun mineLocations(): List<MineLocation> {
            return listOf(MineLocation(0, 0))
        }
    }


}