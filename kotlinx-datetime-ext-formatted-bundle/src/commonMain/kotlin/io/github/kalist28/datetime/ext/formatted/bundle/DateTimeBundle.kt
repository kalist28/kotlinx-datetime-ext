@file:Suppress("unused")
@file:OptIn(ExperimentalTime::class)

/**
 * DateTimeBundle class and related extensions.
 *
 * Provides a comprehensive data structure for formatted date/time information
 * with various representations (timestamp, ISO, relative time, localized strings).
 */
package io.github.kalist28.datetime.ext.formatted.bundle

import io.github.kalist28.datetime.ext.dateTimeNow
import io.github.kalist28.datetime.ext.minus
import io.github.kalist28.datetime.ext.plus
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlin.time.Clock
import kotlin.time.Duration.Companion.days
import kotlin.time.ExperimentalTime

/**
 * Comprehensive date/time information bundle.
 *
 * Contains multiple representations of a date/time value:
 * - Unix timestamp
 * - Human-readable relative time ("5 минут назад")
 * - Formatted dates and times
 * - ISO format with timezone
 * - Timezone information
 *
 * @property timestamp Unix timestamp (seconds since epoch)
 * @property diffForHumans Relative time description (e.g., "5 минут назад")
 * @property dateForHuman Short date format (e.g., "2025-09-20")
 * @property dateForHumanFull Full date format (e.g., "20 сентября 2025г.")
 * @property dateForHumanFullWithTime Full date with time (e.g., "20 сентября 2025г. в 19:11:08")
 * @property iso ISO 8601 format (e.g., "2025-09-20T19:11:08.000000+03:00")
 * @property time Time in HH:mm:ss format
 * @property timezone Timezone name (e.g., "Europe/Moscow")
 * @property timezoneType Always 3 for PHP compatibility
 * @property timeShort Time in HH:mm format
 * @property isFuture Whether the datetime is in the future
 */
data class DateTimeBundle(
    val timestamp: Long,
    val diffForHumans: String,
    val dateForHuman: String,
    val dateForHumanFull: String,
    val dateForHumanFullWithTime: String,
    val iso: String,
    val time: String,
    val timezone: String,
    val timezoneType: Int,
    val timeShort: String,
    val isFuture: Boolean
) {
    /**
     * Converts this bundle's datetime to a LocalDate in the specified timezone.
     *
     * @param timeZone The timezone for conversion (parameter for compatibility, data is already formatted)
     * @return LocalDate represented by this bundle
     */
    fun toLocalDate(
        timeZone: TimeZone = TimeZone.currentSystemDefault()
    ): LocalDate {
        // Parse ISO string to get year, month, day
        val isoDate = dateForHuman // Format: "2025-09-20"
        val parts = isoDate.split("-")
        return LocalDate(parts[0].toInt(), parts[1].toInt(), parts[2].toInt())
    }

    /**
     * Checks if this datetime is today.
     *
     * @param timeZone The timezone for comparison. Defaults to system timezone.
     * @return true if this datetime is today
     */
    fun isToday(
        timeZone: TimeZone = TimeZone.currentSystemDefault()
    ): Boolean {
        val now = dateTimeNow(timeZone)
        val bundleDate = toLocalDate(timeZone)
        return bundleDate == now.date
    }

    /**
     * Checks if this datetime is yesterday.
     *
     * @param timeZone The timezone for comparison. Defaults to system timezone.
     * @return true if this datetime is yesterday
     */
    fun isYesterday(
        timeZone: TimeZone = TimeZone.currentSystemDefault()
    ): Boolean {
        val now = dateTimeNow(timeZone)
        val yesterday = now.date - 1.days
        val bundleDate = toLocalDate(timeZone)
        return bundleDate == yesterday
    }

    /**
     * Checks if this datetime is tomorrow.
     *
     * @param timeZone The timezone for comparison. Defaults to system timezone.
     * @return true if this datetime is tomorrow
     */
    fun isTomorrow(
        timeZone: TimeZone = TimeZone.currentSystemDefault()
    ): Boolean {
        val now = dateTimeNow(timeZone)
        val tomorrow = now.date + 1.days
        val bundleDate = toLocalDate(timeZone)
        return bundleDate == tomorrow
    }

    /**
     * Checks if this datetime is in the same week as today.
     *
     * Week starts on Monday (0) and ends on Sunday (6).
     *
     * @param timeZone The timezone for comparison. Defaults to system timezone.
     * @return true if this datetime is in the same week
     */
    fun isThisWeek(
        timeZone: TimeZone = TimeZone.currentSystemDefault()
    ): Boolean {
        val now = dateTimeNow(timeZone)
        val today = now.date
        val bundleDate = toLocalDate(timeZone)

        val weekStart = today - today.dayOfWeek.ordinal.days
        val weekEnd = weekStart + 6.days

        return bundleDate in weekStart..weekEnd
    }

    /**
     * Checks if this datetime is in the same year.
     *
     * @param timeZone The timezone for comparison. Defaults to system timezone.
     * @return true if this datetime is in the same year
     */
    fun isThisYear(
        timeZone: TimeZone = TimeZone.currentSystemDefault()
    ): Boolean {
        val now = dateTimeNow(timeZone)
        val bundleDate = toLocalDate(timeZone)
        return bundleDate.year == now.date.year
    }

    /**
     * Returns short date representation.
     *
     * Format: "20 сен" (day and short month name)
     *
     * @param strings The strings provider for localization
     * @param timeZone The timezone for conversion. Defaults to system timezone.
     * @return Short date string
     */
    @OptIn(DateTimeFormattedBundleExperimentalApi::class)
    fun dateShort(
        strings: DateTimeBundleStrings = DateTimeBundleFormatterConfig.strings,
        timeZone: TimeZone = TimeZone.currentSystemDefault()
    ): String {
        val date = toLocalDate(timeZone)
        val shortMonth = strings.shortMonths[date.month.number - 1]
        return "${date.day} $shortMonth"
    }

    /**
     * Returns date with day of week.
     *
     * Format: "Суббота, 20 сентября" (day of week, day, and month name in genitive)
     *
     * @param strings The strings provider for localization
     * @param timeZone The timezone for conversion. Defaults to system timezone.
     * @return Full date string with day of week
     */
    @OptIn(DateTimeFormattedBundleExperimentalApi::class)
    fun dateWithDayOfWeek(
        strings: DateTimeBundleStrings = DateTimeBundleFormatterConfig.strings,
        timeZone: TimeZone = TimeZone.currentSystemDefault()
    ): String {
        val date = toLocalDate(timeZone)
        val dayOfWeek = strings.daysOfWeek[date.dayOfWeek.ordinal]
        val month = strings.months[date.month.number - 1]
        return "$dayOfWeek, ${date.day} $month"
    }

    /**
     * Returns the relative time (e.g., "5 минут назад").
     *
     * @return Relative time string
     */
    fun relative(): String = diffForHumans

    /**
     * Checks if this datetime is before another.
     *
     * @param other The other datetime
     * @return true if this is before the other
     */
    fun isBefore(other: DateTimeBundle): Boolean = timestamp < other.timestamp

    /**
     * Checks if this datetime is after another.
     *
     * @param other The other datetime
     * @return true if this is after the other
     */
    fun isAfter(other: DateTimeBundle): Boolean = timestamp > other.timestamp

    /**
     * Calculates minutes until/since this datetime (negative if in the past).
     *
     * @return Number of minutes (negative if in the past)
     */
    fun minutesUntil(): Long {
        val nowEpochSeconds = Clock.System.now().epochSeconds
        return (timestamp - nowEpochSeconds) / 60
    }

    /**
     * Calculates hours until/since this datetime (negative if in the past).
     *
     * @return Number of hours (negative if in the past)
     */
    fun hoursUntil(): Long {
        val nowEpochSeconds = Clock.System.now().epochSeconds
        return (timestamp - nowEpochSeconds) / 3600
    }

    /**
     * Calculates days until/since this datetime (negative if in the past).
     *
     * @return Number of days (negative if in the past)
     */
    fun daysUntil(): Long {
        val nowEpochSeconds = Clock.System.now().epochSeconds
        return (timestamp - nowEpochSeconds) / (3600 * 24)
    }
}
