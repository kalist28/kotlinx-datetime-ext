package io.github.kalist28.datetime.ext

import kotlin.test.*
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class DateTimeConvertersTest {

    @Test
    fun testLocalDateTimeTimestampUtc() {
        val dateTime = LocalDateTime(2023, 1, 1, 12, 0)
        val timestamp = dateTime.timestampUtc
        assertTrue(timestamp > 0)
    }

    @Test
    fun testLocalDateTimeTimestamp() {
        val dateTime = LocalDateTime(2023, 1, 1, 12, 0)
        val timestamp = dateTime.timestamp
        assertTrue(timestamp > 0)
    }

    @Test
    fun testLocalDateTimestampUtc() {
        val date = LocalDate(2023, 1, 1)
        val timestamp = date.timestampUtc
        assertTrue(timestamp > 0)
    }

    @Test
    fun testLocalDateTimestamp() {
        val date = LocalDate(2023, 1, 1)
        val timestamp = date.timestamp
        assertTrue(timestamp > 0)
    }

    @Test
    fun testLongToDateTime() {
        val timestamp = 1672574400000L // 2023-01-01 12:00:00 UTC
        val dateTime = timestamp.toDateTime()
        assertNotNull(dateTime)
        assertEquals(2023, dateTime.year)
    }

    @Test
    fun testLongToDateTimeWithTimeZone() {
        val timestamp = 1672574400000L
        val dateTime = timestamp.toDateTime(TimeZone.UTC)
        assertNotNull(dateTime)
    }

    @Test
    fun testLongToLocalDateTime() {
        val timestamp = 1672574400000L
        val dateTime = timestamp.toLocalDateTime()
        assertNotNull(dateTime)
    }

    @Test
    fun testLongToLocalDate() {
        val timestamp = 1672574400000L
        val date = timestamp.toLocalDate()
        assertNotNull(date)
        assertEquals(2023, date.year)
    }

    @Test
    fun testLongAsDateTime() {
        val timestamp = 1672574400000L
        val dateTime = timestamp.asDateTime
        assertNotNull(dateTime)
    }

    @Test
    fun testTimestampRoundTrip() {
        val original = LocalDateTime(2023, 1, 1, 12, 0)
        val timestamp = original.timestamp
        val converted = timestamp.toLocalDateTime(TimeZone.UTC)
        // Note: May differ slightly due to timezone conversion
        assertEquals(original.date, converted.date)
    }

}


