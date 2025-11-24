package io.github.kalist28.datetime.ext

import kotlin.test.*
import kotlinx.datetime.LocalTime

class LocalTimeExtensionsTest {

    @Test
    fun testLocalTimeMinutes() {
        val time = LocalTime(2, 30)
        assertEquals(150, time.minutes, "2:30 should be 150 minutes")
    }

    @Test
    fun testLocalTimeSeconds() {
        val time = LocalTime(2, 30)
        assertEquals(9_000, time.seconds, "2:30 should be 9000 seconds")
    }

    @Test
    fun testMinutesAsLocalTime() {
        val minutes = 150
        val time = minutes.asLocalTime
        assertEquals(LocalTime(2, 30), time)
    }

    @Test
    fun testLocalTimeMinus() {
        val time1 = LocalTime(10, 30)
        val time2 = LocalTime(8, 15)
        val diff = time1 minus time2
        assertEquals(LocalTime(2, 15), diff)
    }

    @Test
    fun testLocalTimeMinusWrapsAround() {
        val time1 = LocalTime(1, 30)
        val time2 = LocalTime(2, 45)
        val diff = time1 minus time2
        assertEquals(LocalTime(22, 45), diff) // Wraps to previous day
    }

    @Test
    fun testLocalTimePlus() {
        val time1 = LocalTime(10, 30)
        val time2 = LocalTime(2, 15)
        val sum = time1 plus time2
        assertEquals(LocalTime(12, 45), sum)
    }

    @Test
    fun testLocalTimePlusWrapsAround() {
        val time1 = LocalTime(22, 30)
        val time2 = LocalTime(2, 15)
        val sum = time1 plus time2
        assertEquals(LocalTime(0, 45), sum) // Wraps around at 24 hours
    }

    @Test
    fun testRoundToHour() {
        val time = LocalTime(10, 45)
        val rounded = time.roundToHour
        assertEquals(LocalTime(11, 0), rounded, "Should round up when minutes >= 30")
    }

    @Test
    fun testRoundToHourDown() {
        val time = LocalTime(10, 15)
        val rounded = time.roundToHour
        assertEquals(LocalTime(10, 0), rounded, "Should round down when minutes < 30")
    }

    @Test
    fun testRoundToHourAt23() {
        val time = LocalTime(23, 45)
        val rounded = time.roundToHour
        assertEquals(LocalTime(23, 0), rounded, "Should not exceed 23 hours")
    }

    @Test
    fun testLocalTimeFrom() {
        val time = localTimeFrom(10, 30)
        assertEquals(LocalTime(10, 30, 0, 0), time)
    }

    @Test
    fun testLocalTimeFromWithSeconds() {
        val time = localTimeFrom(10, 30, 45)
        assertEquals(LocalTime(10, 30, 45, 0), time)
    }

    @Test
    fun testEmptyLocalTime() {
        assertEquals(LocalTime(0, 0, 0, 0), emptyLocalTime)
    }

    @Test
    fun testFormatToClock() {
        val time = LocalTime(9, 5)
        val formatted = time.formatToClock()
        assertEquals("09:05", formatted)
    }

    @Test
    fun testFormatToClockDoubleDigit() {
        val time = LocalTime(10, 30)
        val formatted = time.formatToClock()
        assertEquals("10:30", formatted)
    }

    @Test
    fun testClockToLocalTime() {
        val time = "09:05".clockToLocalTime()
        assertEquals(LocalTime(9, 5, 0), time)
    }

    @Test
    fun testClockToLocalTimeWithSeconds() {
        val time = "09:05:30".clockToLocalTime()
        assertEquals(LocalTime(9, 5, 30), time)
    }

    @Test
    fun testClockToLocalTimeBlank() {
        val time = "".clockToLocalTime()
        assertEquals(LocalTime(0, 0, 0), time)
    }

}


