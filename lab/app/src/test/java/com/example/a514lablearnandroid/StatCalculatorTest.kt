package com.example.a514lablearnandroid

import com.example.a514lablearnandroid.utils.StatCalculator
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Unit tests for StatCalculator logic.
 */
class StatCalculatorTest {

    @Test
    fun testCalculatePower_isCorrect() {
        // str=10, agi=10, int=10 -> (10*2) + 10 + (10/2) = 20 + 10 + 5 = 35
        val result = StatCalculator.calculatePower(10, 10, 10)
        assertEquals(35, result)
    }

    @Test
    fun testCalculatePower_withZero() {
        val result = StatCalculator.calculatePower(0, 0, 0)
        assertEquals(0, result)
    }

    @Test
    fun testCalculatePower_negativeInput() {
        val result = StatCalculator.calculatePower(-1, 10, 10)
        assertEquals(0, result)
    }

    @Test
    fun testGetRank_S() {
        assertEquals("S", StatCalculator.getRank(100))
        assertEquals("S", StatCalculator.getRank(150))
    }

    @Test
    fun testGetRank_A() {
        assertEquals("A", StatCalculator.getRank(75))
        assertEquals("A", StatCalculator.getRank(99))
    }

    @Test
    fun testGetRank_D() {
        assertEquals("D", StatCalculator.getRank(10))
    }
}
