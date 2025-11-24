package io.github.kalist28.datetime.ext

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.hours
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class DateTimeHelpersTest {

    @Test
    fun testAtStartOfDay() {
        val date = LocalDate(2023, 12, 17)
        val startOfDay = date.atStartOfDay()
        assertEquals(LocalDateTime(2023, 12, 17, 0, 0, 0, 0), startOfDay)
    }

    @Test
    fun testAtEndOfDay() {
        val date = LocalDate(2023, 12, 17)
        val endOfDay = date.atEndOfDay()
        assertEquals(LocalDateTime(2023, 12, 17, 23, 59, 59, 999_999_999), endOfDay)
    }

    @Test
    fun testLocalDateTimeDurationUntil() {
        val first = LocalDateTime(2023, 1, 1, 10, 0)
        val second = LocalDateTime(2023, 1, 1, 12, 30)
        val duration = first durationUntil second
        assertEquals(2.5.hours, duration)
    }

    @Test
    fun testLocalDateTimeDurationUntilWithTimeZone() {
        val first = LocalDateTime(2023, 1, 1, 10, 0)
        val second = LocalDateTime(2023, 1, 1, 12, 30)
        val duration = first.durationUntil(second, TimeZone.UTC)
        assertEquals(2.5.hours, duration)
    }

    @Test
    fun testLocalDateDurationUntil() {
        val first = LocalDate(2023, 1, 1)
        val second = LocalDate(2023, 1, 5)
        val duration = first durationUntil second
        assertEquals(4.days, duration)
    }

    @Test
    fun testLocalDateDurationUntilWithTimeZone() {
        val first = LocalDate(2023, 1, 1)
        val second = LocalDate(2023, 1, 5)
        val duration = first.durationUntil(second, TimeZone.UTC)
        assertEquals(4.days, duration)
    }

    @Test
    fun testDurationUntilNegative() {
        val first = LocalDateTime(2023, 1, 1, 12, 0)
        val second = LocalDateTime(2023, 1, 1, 10, 0)
        val duration = first durationUntil second
        assertTrue(duration.isNegative())
    }

}


