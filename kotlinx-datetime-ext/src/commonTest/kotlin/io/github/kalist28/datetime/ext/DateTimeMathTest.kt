package io.github.kalist28.datetime.ext

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class DateTimeMathTest {

    @Test
    fun testLocalDateTimePlusDuration() {
        val dateTime = LocalDateTime(2023, 1, 7, 21, 0)
        val afterFiveDays = dateTime + 5.days
        assertEquals(LocalDateTime(2023, 1, 12, 21, 0), afterFiveDays)
    }

    @Test
    fun testLocalDateTimeMinusDuration() {
        val dateTime = LocalDateTime(2023, 1, 7, 21, 0)
        val beforeThreeHours = dateTime - 3.hours
        assertEquals(LocalDateTime(2023, 1, 7, 18, 0), beforeThreeHours)
    }

    @Test
    fun testLocalDatePlusDuration() {
        val date = LocalDate(2023, 1, 7)
        val afterFiveDays = date + 5.days
        assertEquals(LocalDate(2023, 1, 12), afterFiveDays)
    }

    @Test
    fun testLocalDateMinusDuration() {
        val date = LocalDate(2023, 1, 7)
        val beforeThreeDays = date - 3.days
        assertEquals(LocalDate(2023, 1, 4), beforeThreeDays)
    }

    @Test
    fun testLocalTimePlusDuration() {
        val time = LocalTime(10, 30)
        val afterTwoHours = time + 2.hours
        assertEquals(LocalTime(12, 30), afterTwoHours)
    }

    @Test
    fun testLocalTimePlusDurationWrapsAround() {
        val time = LocalTime(23, 30)
        val afterOneHour = time + 1.hours
        assertEquals(LocalTime(0, 30), afterOneHour)
    }

    @Test
    fun testLocalTimeMinusDuration() {
        val time = LocalTime(10, 30)
        val beforeTwoHours = time - 2.hours
        assertEquals(LocalTime(8, 30), beforeTwoHours)
    }

    @Test
    fun testLocalTimeMinusDurationWrapsAround() {
        val time = LocalTime(1, 30)
        val beforeTwoHours = time - 2.hours
        assertEquals(LocalTime(23, 30), beforeTwoHours)
    }

    @Test
    fun testLocalTimePlusMinutes() {
        val time = LocalTime(10, 30)
        val after45Minutes = time + 45.minutes
        assertEquals(LocalTime(11, 15), after45Minutes)
    }

    @Test
    fun testLocalTimePlusSeconds() {
        val time = LocalTime(10, 30, 30)
        val after30Seconds = time + 30.seconds
        assertEquals(LocalTime(10, 31, 0), after30Seconds)
    }

}


