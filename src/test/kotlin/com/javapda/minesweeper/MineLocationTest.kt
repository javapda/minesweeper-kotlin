package com.javapda.minesweeper

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class MineLocationTest {

    @Test
    fun testHeadingFunctions() {
        val baseLocation=MineLocation(1,1)
        assertEquals(MineLocation(0,0), baseLocation.nw())
        assertEquals(MineLocation(1,0), baseLocation.n())
        assertEquals(MineLocation(2,0), baseLocation.ne())
        assertEquals(MineLocation(0,1), baseLocation.w())
        assertEquals(MineLocation(2,1), baseLocation.e())
        assertEquals(MineLocation(0,2), baseLocation.sw())
        assertEquals(MineLocation(1,2), baseLocation.s())
        assertEquals(MineLocation(2,2), baseLocation.se())
    }

    @ParameterizedTest
    @MethodSource("invalidMineFieldLocationPointData")
    fun constructInvalidMineFieldLocation(pointData: Pair<Int, Int>) {
        assertThrows<IllegalArgumentException> {
            MineLocation(x = pointData.first, y = pointData.second)
        }
    }

    @ParameterizedTest
    @MethodSource("isWithinMineFieldData")
    fun isWithinMineField(data: Pair<MineLocation, Dimensions>) {
        assertTrue(data.first.isWithinMineField(data.second))
    }

    @ParameterizedTest
    @MethodSource("isNotWithinMineFieldData")
    fun isInvalidMineFieldLocation(data: Pair<MineLocation, Dimensions>) {
        assertTrue(data.first.isNotWithinMineField(data.second))
    }

    companion object {
        @JvmStatic
        fun isWithinMineFieldData(): List<Pair<MineLocation, Dimensions>> {
            return listOf(
                MineLocation(0, 0) to Dimensions(2, 2),
            )
        }

        @JvmStatic
        fun isNotWithinMineFieldData(): List<Pair<MineLocation, Dimensions>> {
            return listOf(
                MineLocation(2, 0) to Dimensions(2, 2),
                MineLocation(2, 2) to Dimensions(2, 2),
            )
        }

        @JvmStatic
        fun invalidMineFieldLocationPointData(): List<Pair<Int, Int>> {
            return listOf(-1 to -1, -1 to 0, 0 to -1)
        }
    }
}