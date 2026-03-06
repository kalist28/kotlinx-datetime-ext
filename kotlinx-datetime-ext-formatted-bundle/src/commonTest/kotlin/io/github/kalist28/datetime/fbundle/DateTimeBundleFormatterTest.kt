package io.github.kalist28.datetime.fbundle

import io.github.kalist28.datetime.ext.formatted.bundle.DateTimeBundleFormatter
import io.github.kalist28.datetime.ext.formatted.bundle.DateTimeFormattedBundleExperimentalApi
import io.github.kalist28.datetime.ext.formatted.bundle.EnglishDateTimeBundleStrings
import io.github.kalist28.datetime.ext.formatted.bundle.RussianDateTimeBundleStrings
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlin.collections.get
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.assertFalse
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class, DateTimeFormattedBundleExperimentalApi::class)
class DateTimeBundleFormatterTest {

    private val formatter = DateTimeBundleFormatter(
        timezone = TimeZone.UTC,
        strings = RussianDateTimeBundleStrings()
    )

    private val fixedNow = LocalDateTime(2025, 9, 20, 20, 0, 0)

    @Test
    fun testJustNow() {
        val dateTime = LocalDateTime(2025, 9, 20, 20, 0, 0)
        val bundle = formatter.from(dateTime, fixedNow)
        assertEquals("только что", bundle.diffForHumans)
        assertFalse(bundle.isFuture)
    }

    @Test
    fun testMinutesAgo() {
        val dateTime = LocalDateTime(2025, 9, 20, 19, 55, 0)
        val bundle = formatter.from(dateTime, fixedNow)
        assertEquals("5 минут назад", bundle.diffForHumans)
        assertFalse(bundle.isFuture)
    }

    @Test
    fun testMinutesInFuture() {
        val dateTime = LocalDateTime(2025, 9, 20, 20, 5, 0)
        val bundle = formatter.from(dateTime, fixedNow)
        assertEquals("через 5 минут", bundle.diffForHumans)
        assertTrue(bundle.isFuture)
    }

    @Test
    fun testHoursAgo() {
        val dateTime = LocalDateTime(2025, 9, 20, 17, 0, 0)
        val bundle = formatter.from(dateTime, fixedNow)
        assertEquals("3 часа назад", bundle.diffForHumans)
        assertFalse(bundle.isFuture)
    }

    @Test
    fun testDaysAgo() {
        val dateTime = LocalDateTime(2025, 9, 15, 20, 0, 0)
        val bundle = formatter.from(dateTime, fixedNow)
        assertEquals("5 дней назад", bundle.diffForHumans)
        assertFalse(bundle.isFuture)
    }

    @Test
    fun testMonthsAgo() {
        val dateTime = LocalDateTime(2025, 7, 20, 20, 0, 0)
        val bundle = formatter.from(dateTime, fixedNow)
        assertEquals("2 месяца назад", bundle.diffForHumans)
        assertFalse(bundle.isFuture)
    }

    @Test
    fun testYearsAgo() {
        val dateTime = LocalDateTime(2023, 9, 20, 20, 0, 0)
        val bundle = formatter.from(dateTime, fixedNow)
        assertEquals("2 года назад", bundle.diffForHumans)
        assertFalse(bundle.isFuture)
    }

    @Test
    fun testDateFormatting() {
        val dateTime = LocalDateTime(2025, 9, 20, 19, 11, 8)
        val bundle = formatter.from(dateTime, fixedNow)
        assertEquals("2025-09-20", bundle.dateForHuman)
        assertEquals("20 сентября 2025г.", bundle.dateForHumanFull)
    }

    @Test
    fun testTimeFormatting() {
        val dateTime = LocalDateTime(2025, 9, 20, 9, 5, 3)
        val bundle = formatter.from(dateTime, fixedNow)
        assertEquals("09:05:03", bundle.time)
        assertEquals("09:05", bundle.timeShort)
    }

    @Test
    fun testTimeFormattingWithZeros() {
        val dateTime = LocalDateTime(2025, 9, 20, 0, 0, 0)
        val bundle = formatter.from(dateTime, fixedNow)
        assertEquals("00:00:00", bundle.time)
        assertEquals("00:00", bundle.timeShort)
    }

    @Test
    fun testIsoFormat() {
        val dateTime = LocalDateTime(2025, 9, 20, 19, 11, 8)
        val bundle = formatter.from(dateTime, fixedNow)
        assertTrue(bundle.iso.startsWith("2025-09-20T19:11:08."))
        assertTrue(bundle.iso.contains("+") || bundle.iso.contains("-"))
    }

    @Test
    fun testTimestamp() {
        val dateTime = LocalDateTime(2025, 9, 20, 20, 0, 0)
        val bundle = formatter.from(dateTime, fixedNow)
        assertTrue(bundle.timestamp > 0)
    }

    @Test
    fun testPluralForms() {
        // Test singular: 1 minute
        val one = LocalDateTime(2025, 9, 20, 19, 59, 0)
        val bundleOne = formatter.from(one, fixedNow)
        assertEquals("1 минуту назад", bundleOne.diffForHumans)

        // Test few: 21 minutes
        val twentyOne = LocalDateTime(2025, 9, 20, 19, 39, 0)
        val bundleTwentyOne = formatter.from(twentyOne, fixedNow)
        assertEquals("21 минуту назад", bundleTwentyOne.diffForHumans)
    }

    @Test
    fun testTimezoneInfo() {
        val dateTime = LocalDateTime(2025, 9, 20, 20, 0, 0)
        val bundle = formatter.from(dateTime, fixedNow)
        assertEquals("UTC", bundle.timezone)
        assertEquals(3, bundle.timezoneType)
    }
}

@OptIn(ExperimentalTime::class)
class DateTimeBundleFormatterEnglishTest {

    private val formatter = DateTimeBundleFormatter(
        timezone = TimeZone.UTC,
        strings = EnglishDateTimeBundleStrings()
    )

    private val fixedNow = LocalDateTime(2025, 9, 20, 20, 0, 0)

    @Test
    fun testJustNow() {
        val dateTime = LocalDateTime(2025, 9, 20, 20, 0, 0)
        val bundle = formatter.from(dateTime, fixedNow)
        assertEquals("just now", bundle.diffForHumans)
        assertFalse(bundle.isFuture)
    }

    @Test
    fun testMinutesAgo() {
        val dateTime = LocalDateTime(2025, 9, 20, 19, 55, 0)
        val bundle = formatter.from(dateTime, fixedNow)
        assertEquals("5 minutes ago", bundle.diffForHumans)
        assertFalse(bundle.isFuture)
    }

    @Test
    fun testOneMinuteAgo() {
        val dateTime = LocalDateTime(2025, 9, 20, 19, 59, 0)
        val bundle = formatter.from(dateTime, fixedNow)
        assertEquals("1 minute ago", bundle.diffForHumans)
        assertFalse(bundle.isFuture)
    }

    @Test
    fun testInMinutes() {
        val dateTime = LocalDateTime(2025, 9, 20, 20, 5, 0)
        val bundle = formatter.from(dateTime, fixedNow)
        assertEquals("in 5 minutes", bundle.diffForHumans)
        assertTrue(bundle.isFuture)
    }

    @Test
    fun testInOneMinute() {
        val dateTime = LocalDateTime(2025, 9, 20, 20, 1, 0)
        val bundle = formatter.from(dateTime, fixedNow)
        assertEquals("in 1 minute", bundle.diffForHumans)
        assertTrue(bundle.isFuture)
    }

    @Test
    fun testDateFull() {
        val dateTime = LocalDateTime(2025, 9, 20, 19, 11, 8)
        val bundle = formatter.from(dateTime, fixedNow)
        assertEquals("20 September 2025", bundle.dateForHumanFull)
    }

    @Test
    fun testDateConnector() {
        val dateTime = LocalDateTime(2025, 9, 20, 19, 11, 8)
        val bundle = formatter.from(dateTime, fixedNow)
        assertTrue(bundle.dateForHumanFullWithTime.contains(" at "))
    }

    @Test
    fun testMonthNamesInEnglish() {
        val strings = EnglishDateTimeBundleStrings()
        assertEquals("January", strings.months[0])
        assertEquals("December", strings.months[11])
        assertEquals("Sep", strings.shortMonths[8])
    }

    @Test
    fun testDayNamesInEnglish() {
        val strings = EnglishDateTimeBundleStrings()
        assertEquals("Monday", strings.daysOfWeek[0])
        assertEquals("Sunday", strings.daysOfWeek[6])
    }
}
