package io.github.kalist28.datetime.fbundle

import io.github.kalist28.datetime.ext.dateTimeNow
import io.github.kalist28.datetime.ext.formatted.bundle.DateTimeBundleFormatter
import io.github.kalist28.datetime.ext.formatted.bundle.DateTimeFormattedBundleExperimentalApi
import io.github.kalist28.datetime.ext.formatted.bundle.RussianDateTimeBundleStrings
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.assertFalse
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class, DateTimeFormattedBundleExperimentalApi::class)
class DateTimeBundleTest {

    private val formatter = DateTimeBundleFormatter(
        timezone = TimeZone.UTC,
        strings = RussianDateTimeBundleStrings()
    )

    @Test
    fun testIsToday() {
        val today = dateTimeNow(TimeZone.UTC)
        val bundle = formatter.from(today, today)
        assertTrue(bundle.isToday(TimeZone.UTC))
    }

    @Test
    fun testIsYesterday() {
        val today = LocalDateTime(2025, 9, 20, 20, 0, 0)
        val yesterday = LocalDateTime(2025, 9, 19, 15, 0, 0)
        val bundleYesterday = formatter.from(yesterday, today)
        // Check the date in the bundle
        assertEquals("2025-09-19", bundleYesterday.dateForHuman)
        assertTrue(bundleYesterday.diffForHumans.contains("назад"))
    }

    @Test
    fun testIsTomorrow() {
        val today = LocalDateTime(2025, 9, 20, 20, 0, 0)
        val tomorrow = LocalDateTime(2025, 9, 21, 15, 0, 0)
        val bundleTomorrow = formatter.from(tomorrow, today)
        assertEquals("2025-09-21", bundleTomorrow.dateForHuman)
        assertTrue(bundleTomorrow.diffForHumans.contains("через"))
    }

    @Test
    fun testIsThisWeek() {
        val today = LocalDateTime(2025, 9, 20, 20, 0, 0) // Saturday
        val wednesday = LocalDateTime(2025, 9, 17, 10, 0, 0)
        val bundle = formatter.from(wednesday, today)
        assertEquals("2025-09-17", bundle.dateForHuman)
    }

    @Test
    fun testIsThisYear() {
        val now = LocalDateTime(2025, 9, 20, 20, 0, 0)
        val january = LocalDateTime(2025, 1, 15, 10, 0, 0)
        val bundle = formatter.from(january, now)
        assertEquals(2025, bundle.dateForHuman.substring(0, 4).toInt())
    }

    @Test
    fun testIsNotThisYear() {
        val now = LocalDateTime(2025, 9, 20, 20, 0, 0)
        val lastYear = LocalDateTime(2024, 9, 20, 20, 0, 0)
        val bundle = formatter.from(lastYear, now)
        assertEquals(2024, bundle.dateForHuman.substring(0, 4).toInt())
    }

    @Test
    fun testIsBefore() {
        val now = LocalDateTime(2025, 9, 20, 20, 0, 0)
        val earlier = LocalDateTime(2025, 9, 20, 19, 0, 0)
        val later = LocalDateTime(2025, 9, 20, 21, 0, 0)

        val bundleEarlier = formatter.from(earlier, now)
        val bundleLater = formatter.from(later, now)

        assertTrue(bundleEarlier.isBefore(bundleLater))
        assertFalse(bundleLater.isBefore(bundleEarlier))
    }

    @Test
    fun testIsAfter() {
        val now = LocalDateTime(2025, 9, 20, 20, 0, 0)
        val earlier = LocalDateTime(2025, 9, 20, 19, 0, 0)
        val later = LocalDateTime(2025, 9, 20, 21, 0, 0)

        val bundleEarlier = formatter.from(earlier, now)
        val bundleLater = formatter.from(later, now)

        assertTrue(bundleLater.isAfter(bundleEarlier))
        assertFalse(bundleEarlier.isAfter(bundleLater))
    }

    @Test
    fun testMinutesUntilNegative() {
        val now = LocalDateTime(2025, 9, 20, 20, 0, 0)
        val past = LocalDateTime(2025, 9, 20, 19, 55, 0)
        val bundle = formatter.from(past, now)

        val minutesUntil = bundle.minutesUntil()
        assertTrue(minutesUntil < 0, "Past datetime should have negative minutesUntil")
    }

    @Test
    fun testHoursUntilNegative() {
        val now = LocalDateTime(2025, 9, 20, 20, 0, 0)
        val past = LocalDateTime(2025, 9, 20, 17, 0, 0)
        val bundle = formatter.from(past, now)

        val hoursUntil = bundle.hoursUntil()
        assertTrue(hoursUntil < 0, "Past datetime should have negative hoursUntil")
    }

    @Test
    fun testDateShort() {
        val now = LocalDateTime(2025, 9, 20, 20, 0, 0)
        val dateTime = LocalDateTime(2025, 9, 20, 15, 0, 0)
        val bundle = formatter.from(dateTime, now)

        val shortDate = bundle.dateShort(
            RussianDateTimeBundleStrings(),
            TimeZone.UTC
        )
        assertEquals("20 сен", shortDate)
    }

    @Test
    fun testDateWithDayOfWeek() {
        val now = LocalDateTime(2025, 9, 20, 20, 0, 0) // Saturday
        val dateTime = LocalDateTime(2025, 9, 20, 15, 0, 0)
        val bundle = formatter.from(dateTime, now)

        val fullDate = bundle.dateWithDayOfWeek(
            RussianDateTimeBundleStrings(),
            TimeZone.UTC
        )
        assertEquals("Суббота, 20 сентября", fullDate)
    }

    @Test
    fun testRelative() {
        val now = LocalDateTime(2025, 9, 20, 20, 0, 0)
        val dateTime = LocalDateTime(2025, 9, 20, 19, 55, 0)
        val bundle = formatter.from(dateTime, now)

        assertEquals("5 минут назад", bundle.relative())
    }
}
