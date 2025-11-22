package io.github.kalist28.datetime.ext

import kotlin.test.*
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class DateTimeExtensionsTest {

    @Test
    fun testInstantNow() {
        val instant1 = instantNow()
        val instant2 = instantNow()
        
        // Verify that the function returns an Instant
        assertNotNull(instant1)
        assertNotNull(instant2)
        
        // Verify that the second call returns time >= the first one
        assertTrue(instant2 >= instant1, "Second call should return time >= first")
    }

    @Test
    fun testDateNow() {
        val date1 = dateNow()
        val date2 = dateNow()
        
        // Verify that the function returns a LocalDate
        assertNotNull(date1)
        assertNotNull(date2)
        
        // Verify that dates are the same (within the same day)
        assertEquals(date1, date2, "Both calls should return the same date")
        
        // Verify that the date is not in the past
        val today = LocalDate(2024, 1, 1) // Minimum date for validation
        assertTrue(date1 >= today, "Date should be valid")
    }

    @Test
    fun testTimeNow() {
        val time1 = timeNow()
        val time2 = timeNow()
        
        // Verify that the function returns a LocalTime
        assertNotNull(time1)
        assertNotNull(time2)
        
        // Verify that time is valid (within 24 hours)
        assertTrue(time1.hour in 0..23, "Hour should be in range 0-23")
        assertTrue(time1.minute in 0..59, "Minute should be in range 0-59")
        assertTrue(time1.second in 0..59, "Second should be in range 0-59")
    }

    @Test
    fun testDateTimeNow() {
        val dateTime1 = dateTimeNow()
        val dateTime2 = dateTimeNow()
        
        // Verify that the function returns a LocalDateTime
        assertNotNull(dateTime1)
        assertNotNull(dateTime2)
        
        // Verify that date and time are valid
        assertNotNull(dateTime1.date)
        assertNotNull(dateTime1.time)
        
        // Verify that the second call returns time >= the first one
        assertTrue(
            dateTime2.date >= dateTime1.date,
            "Date of second call should be >= first"
        )
    }

    @Test
    fun testDateTimeNowWithTimeZone() {
        val utc = TimeZone.of("UTC")
        val dateTimeUtc = dateTimeNow(utc)
        
        val europeMoscow = TimeZone.of("Europe/Moscow")
        val dateTimeMoscow = dateTimeNow(europeMoscow)
        
        // Verify that the function works with different timezones
        assertNotNull(dateTimeUtc)
        assertNotNull(dateTimeMoscow)
        
        // Verify that dates and times are valid
        assertNotNull(dateTimeUtc.date)
        assertNotNull(dateTimeUtc.time)
        assertNotNull(dateTimeMoscow.date)
        assertNotNull(dateTimeMoscow.time)
    }

    @Test
    fun testDateTimeNowDefaultTimeZone() {
        val dateTimeDefault = dateTimeNow()
        val dateTimeExplicit = dateTimeNow(TimeZone.currentSystemDefault())
        
        // Verify that system timezone is used by default
        assertNotNull(dateTimeDefault)
        assertNotNull(dateTimeExplicit)
        
        // Dates should be the same (within a second)
        assertEquals(
            dateTimeDefault.date,
            dateTimeExplicit.date,
            "Dates should match when using system timezone"
        )
    }

    @Test
    fun testDateNowConsistency() {
        val dateFromDateNow = dateNow()
        val dateFromDateTimeNow = dateTimeNow().date
        
        // Verify that dateNow() returns the same date as dateTimeNow().date
        assertEquals(
            dateFromDateNow,
            dateFromDateTimeNow,
            "dateNow() should return the same date as dateTimeNow().date"
        )
    }

    @Test
    fun testTimeNowConsistency() {
        val timeFromTimeNow = timeNow()
        val timeFromDateTimeNow = dateTimeNow().time
        
        // Verify that timeNow() returns time close to dateTimeNow().time
        // (may differ by a few milliseconds)
        assertEquals(
            timeFromTimeNow.hour,
            timeFromDateTimeNow.hour,
            "Hours should match"
        )
        assertEquals(
            timeFromTimeNow.minute,
            timeFromDateTimeNow.minute,
            "Minutes should match"
        )
    }

}
