package com.example.a514lablearnandroid.utils

/**
 * Utility class for calculating RPG statistics logic.
 * Separated from UI for better testability.
 */
object StatCalculator {
    
    /**
     * Calculates the total power based on Str, Agi, and Int.
     * Formula: (Str * 2) + Agi + (Int / 2)
     */
    fun calculatePower(str: Int, agi: Int, int: Int): Int {
        if (str < 0 || agi < 0 || int < 0) return 0
        return (str * 2) + agi + (int / 2)
    }

    /**
     * Determines the rank based on total power.
     */
    fun getRank(power: Int): String {
        return when {
            power >= 100 -> "S"
            power >= 75 -> "A"
            power >= 50 -> "B"
            power >= 25 -> "C"
            else -> "D"
        }
    }
}
