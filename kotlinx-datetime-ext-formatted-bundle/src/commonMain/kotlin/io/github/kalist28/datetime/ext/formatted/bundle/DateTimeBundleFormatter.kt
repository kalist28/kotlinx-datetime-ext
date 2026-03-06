@file:Suppress("unused")
@file:OptIn(ExperimentalTime::class)

/**
 * DateTimeBundleFormatter for creating comprehensive datetime information bundles.
 *
 * Converts LocalDateTime to DateTimeBundle with all formats (relative, ISO, timestamps, etc).
 */
package io.github.kalist28.datetime.ext.formatted.bundle

import io.github.kalist28.datetime.ext.dateTimeNow
import io.github.kalist28.datetime.ext.durationUntil
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.math.abs
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

/**
 * Formats LocalDateTime into comprehensive DateTimeBundle.
 *
 * Provides multiple representations of a datetime including:
 * - Relative time ("5 минут назад")
 * - ISO 8601 format with timezone
 * - Formatted dates and times
 * - Unix timestamp
 *
 * @property timezone The timezone for all conversions. Defaults to system timezone.
 * @property strings The strings provider for localization. Defaults to Russian.
 */
@OptIn(DateTimeFormattedBundleExperimentalApi::class)
class DateTimeBundleFormatter(
    val timezone: TimeZone = TimeZone.currentSystemDefault(),
    val strings: DateTimeBundleStrings = DateTimeBundleFormatterConfig.strings
) {
    /**
     * Formats a LocalDateTime into DateTimeBundle.
     *
     * @param localDateTime The datetime to format
     * @param now The current datetime. Defaults to current system time. Used for relative time calculation.
     * @return Formatted DateTimeBundle
     */
    fun from(
        localDateTime: LocalDateTime,
        now: LocalDateTime = dateTimeNow(timezone)
    ): DateTimeBundle {
        val instant = localDateTime.toInstant(timezone)
        val nowInstant = now.toInstant(timezone)
        val timestamp = instant.epochSeconds
        val isFuture = localDateTime > now

        // Calculate relative time
        val diffForHumans = calculateRelativeTime(instant, nowInstant, isFuture)

        // Format date components
        val dateForHuman = formatDate(localDateTime)
        val dateForHumanFull = formatDateFull(localDateTime)
        val dateForHumanFullWithTime = formatDateFullWithTime(localDateTime)

        // Format time components
        val time = formatTime(localDateTime)
        val timeShort = formatTimeShort(localDateTime)

        // Format ISO with timezone offset
        val iso = formatIso(localDateTime, timezone)

        return DateTimeBundle(
            timestamp = timestamp,
            diffForHumans = diffForHumans,
            dateForHuman = dateForHuman,
            dateForHumanFull = dateForHumanFull,
            dateForHumanFullWithTime = dateForHumanFullWithTime,
            iso = iso,
            time = time,
            timezone = timezone.toString(),
            timezoneType = 3,
            timeShort = timeShort,
            isFuture = isFuture
        )
    }

    /**
     * Calculates relative time string (e.g., "5 минут назад").
     */
    private fun calculateRelativeTime(
        instant: Instant,
        nowInstant: Instant,
        isFuture: Boolean
    ): String {
        val diffSeconds = abs(nowInstant.epochSeconds - instant.epochSeconds)
        val diffMinutes = diffSeconds / 60
        val diffHours = diffMinutes / 60
        val diffDays = diffHours / 24
        val diffMonths = diffDays / 30
        val diffYears = diffDays / 365

        return when {
            diffMinutes < 1 -> {
                if (isFuture) strings.inFewSeconds else strings.justNow
            }
            diffMinutes < 60 -> {
                if (isFuture) strings.inMinutes(diffMinutes) else strings.minutesAgo(diffMinutes)
            }
            diffHours < 24 -> {
                if (isFuture) strings.inHours(diffHours) else strings.hoursAgo(diffHours)
            }
            diffDays < 30 -> {
                if (isFuture) strings.inDays(diffDays) else strings.daysAgo(diffDays)
            }
            diffMonths < 12 -> {
                if (isFuture) strings.inMonths(diffMonths) else strings.monthsAgo(diffMonths)
            }
            else -> {
                if (isFuture) strings.inYears(diffYears) else strings.yearsAgo(diffYears)
            }
        }
    }

    /**
     * Formats date as "yyyy-MM-dd".
     */
    private fun formatDate(dt: LocalDateTime): String {
        return "${dt.year}-${dt.monthNumber.zeroPad(2)}-${dt.dayOfMonth.zeroPad(2)}"
    }

    /**
     * Formats date as "20 сентября 2025г.".
     */
    private fun formatDateFull(dt: LocalDateTime): String {
        val month = strings.months[dt.monthNumber - 1]
        return "${dt.dayOfMonth} $month ${dt.year}${strings.yearSuffix}"
    }

    /**
     * Formats date with time as "20 сентября 2025г. в 19:11:08".
     */
    private fun formatDateFullWithTime(dt: LocalDateTime): String {
        val dateStr = formatDateFull(dt)
        val timeStr = formatTime(dt)
        return "$dateStr ${strings.dateConnector} $timeStr"
    }

    /**
     * Formats time as "HH:mm:ss".
     */
    private fun formatTime(dt: LocalDateTime): String {
        return "${dt.hour.zeroPad(2)}:${dt.minute.zeroPad(2)}:${dt.second.zeroPad(2)}"
    }

    /**
     * Formats time as "HH:mm".
     */
    private fun formatTimeShort(dt: LocalDateTime): String {
        return "${dt.hour.zeroPad(2)}:${dt.minute.zeroPad(2)}"
    }

    /**
     * Formats datetime in ISO 8601 format with timezone offset.
     *
     * Example: "2025-09-20T19:11:08.000000+03:00"
     */
    private fun formatIso(dt: LocalDateTime, tz: TimeZone): String {
        val instant = dt.toInstant(tz)

        // Calculate offset by comparing UTC time with local time
        val utcDateTime = instant.toLocalDateTime(TimeZone.UTC)
        val durationDiff = utcDateTime.durationUntil(dt, TimeZone.UTC)
        val offsetMinutes = durationDiff.inWholeMinutes.toInt()
        val offsetHours = offsetMinutes / 60
        val offsetMins = abs(offsetMinutes % 60)
        val offsetSign = if (offsetMinutes >= 0) "+" else "-"

        val microseconds = (dt.nanosecond / 1000).zeroPad(6)
        val offsetStr = "$offsetSign${abs(offsetHours).zeroPad(2)}:${offsetMins.zeroPad(2)}"

        return "${formatDate(dt)}T${formatTime(dt)}.$microseconds$offsetStr"
    }

    /**
     * Zero-pads an integer to a specific width.
     *
     * @param width The minimum width of the result
     * @return Zero-padded string
     */
    private fun Int.zeroPad(width: Int): String =
        toString().padStart(width, '0')
}
