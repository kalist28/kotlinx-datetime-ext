package io.github.kalist28.datetime.ext

import kotlin.test.*
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class DateTimeQueriesTest {

    @Test
    fun testLocalDateTimeIsPast() {
        val pastDate = LocalDateTime(2020, 1, 1, 12, 0)
        assertTrue(pastDate.isPast, "Past date should be in the past")
    }

    @Test
    fun testLocalDateTimeIsNotPast() {
        val futureDate = LocalDateTime(2100, 1, 1, 12, 0)
        assertFalse(futureDate.isPast, "Future date should not be in the past")
    }

    @Test
    fun testLocalDateIsPast() {
        val pastDate = LocalDate(2020, 1, 1)
        assertTrue(pastDate.isPast, "Past date should be in the past")
    }

    @Test
    fun testLocalDateIsNotPast() {
        val futureDate = LocalDate(2100, 1, 1)
        assertFalse(futureDate.isPast, "Future date should not be in the past")
    }

    @Test
    fun testIsHolidaySaturday() {
        val saturday = LocalDate(2023, 1, 7) // Saturday
        assertTrue(saturday.isHoliday, "Saturday should be a holiday")
    }

    @Test
    fun testIsHolidaySunday() {
        val sunday = LocalDate(2023, 1, 8) // Sunday
        assertTrue(sunday.isHoliday, "Sunday should be a holiday")
    }

    @Test
    fun testIsNotHolidayMonday() {
        val monday = LocalDate(2023, 1, 9) // Monday
        assertFalse(monday.isHoliday, "Monday should not be a holiday")
    }

    @Test
    fun testIsWeekday() {
        val monday = LocalDate(2023, 1, 9) // Monday
        assertTrue(monday.isWeekday, "Monday should be a weekday")
    }

    @Test
    fun testIsNotWeekday() {
        val saturday = LocalDate(2023, 1, 7) // Saturday
        assertFalse(saturday.isWeekday, "Saturday should not be a weekday")
    }

    @Test
    fun testIsLeapYear() {
        val leapYearDate = LocalDate(2020, 1, 1)
        assertTrue(leapYearDate.isLeapYear, "2020 should be a leap year")
    }

    @Test
    fun testIsNotLeapYear() {
        val notLeapYearDate = LocalDate(2021, 1, 1)
        assertFalse(notLeapYearDate.isLeapYear, "2021 should not be a leap year")
    }

    @Test
    fun testIntIsLeap() {
        assertTrue(2020.isLeap(), "2020 should be a leap year")
        assertTrue(2000.isLeap(), "2000 should be a leap year")
        assertFalse(2021.isLeap(), "2021 should not be a leap year")
        assertFalse(1900.isLeap(), "1900 should not be a leap year (divisible by 100 but not 400)")
    }

    @Test
    fun testDaysInMonth() {
        val january = LocalDate(2023, 1, 1)
        assertEquals(31, january.daysInMonth, "January should have 31 days")
        
        val february = LocalDate(2023, 2, 1)
        assertEquals(28, february.daysInMonth, "February 2023 should have 28 days")
        
        val februaryLeap = LocalDate(2020, 2, 1)
        assertEquals(29, februaryLeap.daysInMonth, "February 2020 should have 29 days (leap year)")
        
        val april = LocalDate(2023, 4, 1)
        assertEquals(30, april.daysInMonth, "April should have 30 days")
    }

    @Test
    fun testDaysInYear() {
        val leapYear = LocalDate(2020, 1, 1)
        assertEquals(366, leapYear.daysInYear, "Leap year should have 366 days")
        
        val notLeapYear = LocalDate(2021, 1, 1)
        assertEquals(365, notLeapYear.daysInYear, "Non-leap year should have 365 days")
    }

}


